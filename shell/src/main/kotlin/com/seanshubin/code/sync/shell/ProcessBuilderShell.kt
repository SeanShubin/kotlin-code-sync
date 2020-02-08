package com.seanshubin.code.sync.shell

class ProcessBuilderShell(private val execEvent: (ShellCommand) -> Unit) : Shell {
    override fun exec(shellCommand: ShellCommand) {
        execEvent(shellCommand)
//        val (directory, command) = shellCommand
//        val processBuilder= ProcessBuilder()
//        processBuilder.command(command)
//        processBuilder.directory(directory.toFile())
    }
}