package com.seanshubin.code.sync.shell

import kotlinx.coroutines.CoroutineDispatcher

interface Shell {
    fun exec(shellCommand: ShellCommand, coroutineDispatcher: CoroutineDispatcher)
  fun execWithResult(shellCommand: ShellCommand, coroutineDispatcher: CoroutineDispatcher): ShellResult
}
