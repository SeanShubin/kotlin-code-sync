package com.seanshubin.code.sync.domain

interface GithubProjectFinder {
  fun findAll():List<GithubProject>
}
