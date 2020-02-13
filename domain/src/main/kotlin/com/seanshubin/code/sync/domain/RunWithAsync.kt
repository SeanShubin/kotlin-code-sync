package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.Shell
import com.seanshubin.code.sync.shell.ShellCommand
import java.nio.file.Path

class RunWithAsync(private val githubProjectFinder: GithubProjectFinder,
                   private val localProjectFinder: LocalProjectFinder,
                   private val localGithubDirectory: Path,
                   private val shell: Shell,
                   private val projectSyncedEvent: (String, ProjectStatus) -> Unit) : Runnable {
    override fun run() {
        val remote = githubProjectFinder.findAll().sorted()
        val local = localProjectFinder.findAll().sorted()
        val missing = remote - local
        val extra = local - remote
        val projectNames = (local intersect remote).sorted()
        missing.forEach(createEvent(ProjectStatus.CLONE_LOCALLY))
        extra.forEach(createEvent(ProjectStatus.CREATE_IN_GITHUB))
        projectNames.forEach(::statusEvent)
    }

    private fun createEvent(status: ProjectStatus): (String) -> Unit =
            { name: String -> projectSyncedEvent(name, status) }

    private fun statusEvent(projectName: String) {
        val directory = localGithubDirectory.resolve(projectName)
        val command = listOf("git", "status", "-s")
        val shellCommand = ShellCommand(directory, command)
        val result = shell.execWithResult(shellCommand)
        if (result.outputLines.isEmpty()) {
            projectSyncedEvent(projectName, ProjectStatus.IN_SYNC)
        } else {
            projectSyncedEvent(projectName, ProjectStatus.PENDING_EDITS)
        }
    }
}
