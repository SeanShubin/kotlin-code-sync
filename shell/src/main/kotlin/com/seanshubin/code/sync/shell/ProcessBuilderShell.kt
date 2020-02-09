package com.seanshubin.code.sync.shell

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.Charset

class ProcessBuilderShell(private val charset: Charset,
                          private val execEvent: (ShellCommand) -> Unit,
                          private val outputLineEvent: (String) -> Unit,
                          private val errorLineEvent: (String) -> Unit) : Shell {
    override fun exec(shellCommand: ShellCommand, coroutineDispatcher: CoroutineDispatcher) {
        execEvent(shellCommand)
        val (directory, command) = shellCommand
        val processBuilder = ProcessBuilder()
        processBuilder.command(command)
        processBuilder.directory(directory.toFile())
        val process = processBuilder.start()
        GlobalScope.launch(coroutineDispatcher) {
            IoUtil.inputStreamToLineEvent(process.inputStream, charset, outputLineEvent)
        }
        GlobalScope.launch(coroutineDispatcher) {
            IoUtil.inputStreamToLineEvent(process.errorStream, charset, errorLineEvent)
        }
        process.waitFor()
    }
}
