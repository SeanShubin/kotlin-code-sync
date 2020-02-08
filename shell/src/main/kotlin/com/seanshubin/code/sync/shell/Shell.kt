package com.seanshubin.code.sync.shell

interface Shell {
    fun exec(shellCommand: ShellCommand)
}
