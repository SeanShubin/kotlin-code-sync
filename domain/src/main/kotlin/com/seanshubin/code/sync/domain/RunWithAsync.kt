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
        val pending = hasPendingEdits(projectName)
        val local = hasLocalCommits(projectName)
        val remote = hasRemoteCommits(projectName)
        if (pending || local || remote) {
            if (pending) {
                projectSyncedEvent(projectName, ProjectStatus.PENDING_EDITS)
            }
            if (local) {
                projectSyncedEvent(projectName, ProjectStatus.LOCAL_COMMITS)
            }
            if (remote) {
                projectSyncedEvent(projectName, ProjectStatus.REMOTE_COMMITS)
            }
        } else {
            projectSyncedEvent(projectName, ProjectStatus.IN_SYNC)
        }
    }

    private fun hasPendingEdits(projectName: String): Boolean {
        return resultNonEmpty(projectName, "git", "status", "-s")
    }

    private fun hasLocalCommits(projectName: String): Boolean {
        return resultNonEmpty(projectName, "git", "log", "--oneline", "@{u}..")
    }

    private fun hasRemoteCommits(projectName: String): Boolean {
        return resultNonEmpty(projectName, "git", "log", "--oneline", "..@{u}")
    }

    private fun resultNonEmpty(projectName: String, vararg command: String): Boolean {
        val directory = localGithubDirectory.resolve(projectName)
        val shellCommand = ShellCommand(directory, command.toList())
        val result = shell.execWithResult(shellCommand)
        return result.outputLines.isNotEmpty()
    }
}
