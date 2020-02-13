package com.seanshubin.code.sync.console

import com.fasterxml.jackson.databind.ObjectMapper
import com.seanshubin.code.sync.contract.FilesContract
import com.seanshubin.code.sync.contract.FilesDelegate
import com.seanshubin.code.sync.domain.*
import com.seanshubin.code.sync.logger.LogGroup
import com.seanshubin.code.sync.logger.Logger
import com.seanshubin.code.sync.logger.LoggerFactory
import com.seanshubin.code.sync.shell.ProcessBuilderShell
import com.seanshubin.code.sync.shell.Shell
import com.seanshubin.code.sync.shell.ShellCommand
import kotlinx.coroutines.CoroutineDispatcher
import java.net.http.HttpClient
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path

class DependencyInjectionCoroutineDispatcher(configuration: Configuration, coroutineDispatcher: CoroutineDispatcher) {
    private val objectMapper: ObjectMapper = JsonUtil.objectMapper
    private val logDir: Path = configuration.logDir.value
    private val logGroup: LogGroup = LoggerFactory.instanceDefaultZone.createLogGroup(logDir)
    private val notificationsLogger: Logger = logGroup.create("notifications")
    private val notifications: Notifications = LoggingNotifications(notificationsLogger)
    private val httpClient: HttpClient = HttpClient.newBuilder().build()
    private val http: Http = JavaHttp(httpClient, notifications::httpRequest)
    private val githubUserName: String = configuration.githubUserName.value
    private val githubProjectDataTransfer: GithubProjectDataTransfer = GithubProjectDataTransferImpl(objectMapper)
    private val githubProjectFinder: GithubProjectFinder = GithubProjectFinderImpl(http, githubUserName, githubProjectDataTransfer)
    private val execEvent: (ShellCommand) -> Unit = notifications::shellCommand
    private val files: FilesContract = FilesDelegate
    private val localGithubPath: Path = configuration.localGithubPath.value
    private val ignored: List<String> = configuration.ignoreLocalDirNames.value
    private val localProjectFinder: LocalProjectFinder = LocalProjectFinderImpl(localGithubPath, files, ignored)
    private val commandGenerator: CommandGenerator = CommandGeneratorImpl(localGithubPath, githubUserName)
    private val charset: Charset = StandardCharsets.UTF_8
    private val outputLineEvent: (String) -> Unit = notifications::outputLineEvent
    private val errorLineEvent: (String) -> Unit = notifications::errorLineEvent
    private val shell: Shell = ProcessBuilderShell(coroutineDispatcher, charset, execEvent, outputLineEvent, errorLineEvent)
    private val projectFactory: ProjectFactory = ProjectFactoryImpl(shell, localGithubPath)
    private val projectSyncedEvent: (ProjectAndStatus) -> Unit = notifications::projectSyncedEvent
    val runner: Runnable = RunWithAsync(githubProjectFinder, localProjectFinder, commandGenerator, shell, projectFactory, projectSyncedEvent)
}