package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.ShellCommand
import java.net.http.HttpRequest

interface Notifications {
    fun httpRequest(request: HttpRequest)
    fun shellCommand(shellCommand: ShellCommand)
    fun outputLineEvent(line: String)
    fun errorLineEvent(line: String)
    fun projectSyncedEvent(name: String, status: ProjectStatus)
}