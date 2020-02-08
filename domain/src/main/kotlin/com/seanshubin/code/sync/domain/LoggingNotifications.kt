package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.logger.Logger
import com.seanshubin.code.sync.shell.ShellCommand
import java.net.http.HttpRequest

class LoggingNotifications(private val logger: Logger) :Notifications {
  override fun shellCommand(shellCommand: ShellCommand) {
    val (directory, command) = shellCommand
    val commandString = command.joinToString(" ")
    val message = "$directory> $commandString"
    logger.log(message)
  }

  override fun httpRequest(request: HttpRequest) {
    logger.log("${request.method()} ${request.uri()}")
  }
}
