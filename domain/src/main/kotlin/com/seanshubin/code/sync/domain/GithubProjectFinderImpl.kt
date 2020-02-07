package com.seanshubin.code.sync.domain

class GithubProjectFinderImpl(private val http:Http,
                              private val userName:String,
                              private val githubProjectDataTransfer: GithubProjectDataTransfer):GithubProjectFinder {
  override fun findAll(): List<GithubProject> {
    val jsonInputStream = http.getInputStream("https://api.github.com/users/$userName/repos?per_page=100")
    val githubProjects = githubProjectDataTransfer.fromInputStream(jsonInputStream)
    return githubProjects
  }
}
