package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher

interface ProjectApi {
  fun existsInGitlab(name: String): Boolean
  fun existsLocally(name: String): Boolean
  fun hasPendingEdits(name: String, coroutineDispatcher: CoroutineDispatcher): Boolean
}