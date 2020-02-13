package com.seanshubin.code.sync.domain

interface ProjectApi {
  fun existsInGitlab(name: String): Boolean
  fun existsLocally(name: String): Boolean
  fun hasPendingEdits(name: String): Boolean
}