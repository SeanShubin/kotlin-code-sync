package com.seanshubin.code.sync.domain

import java.net.http.HttpRequest

interface Notifications {
  fun httpRequest(request:HttpRequest)
  fun githubProject(githubProject:GithubProject)
}