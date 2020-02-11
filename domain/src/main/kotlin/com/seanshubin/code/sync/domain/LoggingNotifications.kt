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

    override fun outputLineEvent(line: String) {
        logger.log("OUT: $line")
    }

    override fun errorLineEvent(line: String) {
        logger.log("ERR:  $line")
    }

    override fun projectSyncedEvent(projectAndStatus: ProjectAndStatus) {
        val name = projectAndStatus.project.name
        val status = projectAndStatus.status
        logger.log("$name -> $status")
    }
}
