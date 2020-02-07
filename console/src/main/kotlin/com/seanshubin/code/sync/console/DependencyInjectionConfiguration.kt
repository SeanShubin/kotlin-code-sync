package com.seanshubin.code.sync.console

import com.fasterxml.jackson.databind.ObjectMapper
import com.seanshubin.code.sync.domain.*
import com.seanshubin.code.sync.logger.LogGroup
import com.seanshubin.code.sync.logger.Logger
import com.seanshubin.code.sync.logger.LoggerFactory
import java.net.http.HttpClient
import java.nio.file.Path

class DependencyInjectionConfiguration(configuration: Configuration) {
  val objectMapper: ObjectMapper = JsonUtil.objectMapper
  val logDir: Path = configuration.logDir.value
  val logGroup: LogGroup = LoggerFactory.instanceDefaultZone.createLogGroup(logDir)
  val notificationsLogger: Logger = logGroup.create("notifications")
  val notifications:Notifications = LoggingNotifications(notificationsLogger)
  val httpClient:HttpClient = HttpClient.newBuilder().build()
  val http:Http = JavaHttp(httpClient, notifications::httpRequest)
  val userName:String = configuration.githubName.value
  val githubProjectDataTransfer:GithubProjectDataTransfer = GithubProjectDataTransferImpl(objectMapper)
  val githubProjectFinder: GithubProjectFinder = GithubProjectFinderImpl(http, userName, githubProjectDataTransfer)
  val githubProjectEvent: (GithubProject) -> Unit = notifications::githubProject
  val runner:Runnable = Runner(githubProjectFinder, githubProjectEvent)
}
