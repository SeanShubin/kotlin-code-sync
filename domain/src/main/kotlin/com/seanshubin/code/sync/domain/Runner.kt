package com.seanshubin.code.sync.domain

class Runner(private val githubProjectFinder:GithubProjectFinder,
             private val githubProjectEvent:(GithubProject) -> Unit): Runnable {
  override fun run() {
    val projects = githubProjectFinder.findAll()
    projects.forEach(githubProjectEvent)
  }
}
