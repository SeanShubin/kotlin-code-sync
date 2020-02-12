package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher

interface Project {
    val name: String
    fun existsInGitlab(): Boolean
    fun existsLocally(): Boolean
    fun hasPendingEdits(coroutineDispatcher: CoroutineDispatcher): Boolean
    fun hasRemoteCommits(): Boolean
    fun hasLocalCommits(): Boolean
    fun clone()
    fun fetch()
    fun rebase()
    fun push()
}
