package com.seanshubin.code.sync.domain

class Runner(private val githubProjectFinder: GithubProjectFinder,
             private val localProjectFinder: LocalProjectFinder,
             private val commandGenerator: CommandGenerator,
             private val commandEvent: (String) -> Unit) : Runnable {
  override fun run() {
    val remote = githubProjectFinder.findAll()
    val local = localProjectFinder.findAll();
    val missing = remote - local
    val extra = local - remote
    val downloadCommands = missing.flatMap(commandGenerator::cloneFromGithubToLocal)
    val uploadCommands = extra.flatMap(commandGenerator::addLocalToGithub)
    val commands = downloadCommands + uploadCommands
    commands.forEach(commandEvent)
  }
}
