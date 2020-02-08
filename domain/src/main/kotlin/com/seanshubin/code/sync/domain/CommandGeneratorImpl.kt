package com.seanshubin.code.sync.domain

class CommandGeneratorImpl(private val githubUserName: String) : CommandGenerator {
    override fun cloneFromGithubToLocal(name: String): List<String> {
        val cloneCommand = "git clone git@github.com:$githubUserName/$name.git"
        return listOf(cloneCommand)
    }

    override fun addLocalToGithub(name: String): List<String> {
        val commands = listOf(
                "git init",
                "git add --all",
                "git commit -m \"$name\"",
                "git remote add origin git@github.com:$githubUserName/$name.git",
                "git push -u origin master")
        return commands
    }
}
