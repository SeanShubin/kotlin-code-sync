package com.seanshubin.code.sync.console

import com.fasterxml.jackson.databind.ObjectMapper
import com.seanshubin.code.sync.domain.*
import com.seanshubin.code.sync.logger.LogGroup
import com.seanshubin.code.sync.logger.Logger
import com.seanshubin.code.sync.logger.LoggerFactory
import java.net.http.HttpClient
import java.nio.file.Path

class DependencyInjectionConfiguration(configuration: Configuration) {
  private val objectMapper: ObjectMapper = JsonUtil.objectMapper
  private val logDir: Path = configuration.logDir.value
  private val logGroup: LogGroup = LoggerFactory.instanceDefaultZone.createLogGroup(logDir)
  private val notificationsLogger: Logger = logGroup.create("notifications")
  private val notifications:Notifications = LoggingNotifications(notificationsLogger)
  private val httpClient:HttpClient = HttpClient.newBuilder().build()
  private val http:Http = JavaHttp(httpClient, notifications::httpRequest)
  private val userName:String = configuration.githubName.value
  private val githubProjectDataTransfer:GithubProjectDataTransfer = GithubProjectDataTransferImpl(objectMapper)
  private val githubProjectFinder: GithubProjectFinder = GithubProjectFinderImpl(http, userName, githubProjectDataTransfer)
  private val githubProjectEvent: (GithubProject) -> Unit = notifications::githubProject
  val runner:Runnable = Runner(githubProjectFinder, githubProjectEvent)
}
