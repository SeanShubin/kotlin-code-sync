package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.Shell
import com.seanshubin.code.sync.shell.ShellCommand
import kotlinx.coroutines.CoroutineDispatcher
import java.nio.file.Path

class ProjectApiImpl(private val shell: Shell,
                     private val localBasePath: Path,
                     private val local: List<String>,
                     private val remote: List<String>) : ProjectApi {
  override fun existsInGitlab(name: String): Boolean {
    return remote.contains(name)
  }

  override fun existsLocally(name: String): Boolean {
    return local.contains(name)
  }

  override fun hasPendingEdits(name: String, coroutineDispatcher: CoroutineDispatcher): Boolean {
    val command = listOf("git", "status", "-s")
    val shellCommand = ShellCommand(localBasePath, command)
    val result = shell.execWithResult(shellCommand, coroutineDispatcher)
    return result.outputLines.size > 0
  }
}

