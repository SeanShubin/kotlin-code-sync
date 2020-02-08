package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.logger.Logger
import java.net.http.HttpRequest

class LoggingNotifications(private val logger: Logger) :Notifications{
  override fun consoleCommand(command: String) {
    logger.log(command)
  }

  override fun httpRequest(request: HttpRequest) {
    logger.log("${request.method()} ${request.uri()}")
  }
}
