package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.logger.LogGroup
import com.seanshubin.code.sync.shell.ShellCommand
import java.net.http.HttpRequest

class LoggingNotifications(private val logGroup: LogGroup) : Notifications {
    private val shellLog = logGroup.create("shell")
    private val httpRequestLog = logGroup.create("http")
    private val statusLog = logGroup.create("status")
    private val maxStatusLength: Int = ProjectStatus.values().map { it.toString().length }.max()!!
    override fun shellCommand(shellCommand: ShellCommand) {
        val (directory, command) = shellCommand
        val commandString = command.joinToString(" ")
        val message = "$directory> $commandString"
        shellLog.log(message)
    }

    override fun httpRequest(request: HttpRequest) {
        httpRequestLog.log("${request.method()} ${request.uri()}")
    }

    override fun outputLineEvent(line: String) {
        shellLog.log("OUT: $line")
    }

    override fun errorLineEvent(line: String) {
        shellLog.log("ERR:  $line")
    }

    override fun projectSyncedEvent(name: String, status: ProjectStatus) {
        statusLog.log("%-${maxStatusLength}s: $name".format(status))
    }
}
