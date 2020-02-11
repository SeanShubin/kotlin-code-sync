package com.seanshubin.code.sync.domain

interface Project {
    val name: String
    fun existsInGitlab(): Boolean
    fun existsLocally(): Boolean
    fun hasPendingEdits(): Boolean
    fun hasRemoteCommits(): Boolean
    fun hasLocalCommits(): Boolean
    fun clone()
    fun fetch()
    fun rebase()
    fun push()
}
