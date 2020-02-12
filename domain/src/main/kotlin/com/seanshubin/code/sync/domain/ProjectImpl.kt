package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher

class ProjectImpl(override val name: String,
                  private val projectApi: ProjectApi) : Project {
    override fun existsInGitlab(): Boolean {
        return projectApi.existsInGitlab(name)
    }

    override fun existsLocally(): Boolean {
        return projectApi.existsLocally(name)
    }

    override fun hasPendingEdits(coroutineDispatcher: CoroutineDispatcher): Boolean {
        return projectApi.hasPendingEdits(name, coroutineDispatcher)
    }

    override fun hasRemoteCommits(): Boolean {
        TODO("not implemented")
    }

    override fun hasLocalCommits(): Boolean {
        TODO("not implemented")
    }

    override fun clone() {
        TODO("not implemented")
    }

    override fun fetch() {
        TODO("not implemented")
    }

    override fun rebase() {
        TODO("not implemented")
    }

    override fun push() {
        TODO("not implemented")
    }
}