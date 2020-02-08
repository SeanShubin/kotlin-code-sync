package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.ShellCommand
import java.nio.file.Path

class CommandGeneratorImpl(private val localGithubPath: Path, private val githubUserName: String) : CommandGenerator {
    override fun cloneFromGithubToLocal(name: String): List<ShellCommand> {
        val command = listOf("git", "clone", "git@github.com:$githubUserName/$name.git")
        val cloneCommand = ShellCommand(localGithubPath, command)
        return listOf(cloneCommand)
    }

    override fun addLocalToGithub(name: String): List<ShellCommand> {
        val directory = localGithubPath.resolve(name)
        val commands = listOf(
                listOf("git", "init"),
                listOf("git", "add", "--all"),
                listOf("git", "commit", "-m", name),
                listOf("git", "remote", "add", "origin", "git@github.com:$githubUserName/$name.git"),
                listOf("git", "push", "-u", "origin", "master"))
        return commands.map { ShellCommand(directory, it) }
    }
}
