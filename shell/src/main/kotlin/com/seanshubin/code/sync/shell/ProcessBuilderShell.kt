package com.seanshubin.code.sync.shell

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.nio.charset.Charset

class ProcessBuilderShell(private val coroutineDispatcher: CoroutineDispatcher,
                          private val charset: Charset,
                          private val execEvent: (ShellCommand) -> Unit,
                          private val outputLineEvent: (String) -> Unit,
                          private val errorLineEvent: (String) -> Unit) : Shell {
    override fun exec(shellCommand: ShellCommand) {
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

    override fun execWithResult(shellCommand: ShellCommand): ShellResult {
        execEvent(shellCommand)
        val (directory, command) = shellCommand
        val processBuilder = ProcessBuilder()
        processBuilder.command(command)
        processBuilder.directory(directory.toFile())
        val process = processBuilder.start()
        val outputLineConsumer = LineConsumer()
        val errorLineConsumer = LineConsumer()
        val outputJob = GlobalScope.launch(coroutineDispatcher) {
            IoUtil.inputStreamToLineEvent(process.inputStream, charset, outputLineConsumer)
    }
    val errorJob = GlobalScope.launch(coroutineDispatcher) {
      IoUtil.inputStreamToLineEvent(process.errorStream, charset, errorLineConsumer)
    }
    runBlocking {
      outputJob.join()
      errorJob.join()
    }
    val exitCode = process.waitFor()
    return ShellResult(exitCode, outputLineConsumer.lines, errorLineConsumer.lines)
  }
}
