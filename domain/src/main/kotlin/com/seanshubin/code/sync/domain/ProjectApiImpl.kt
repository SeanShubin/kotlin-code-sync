package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.Shell

class ProjectApiImpl(private val local: List<String>,
                     private val remote: List<String>,
                     private val shell: Shell) : ProjectApi {
    override fun existsInGitlab(name: String): Boolean {
        return remote.contains(name)
    }

    override fun existsLocally(name: String): Boolean {
        return local.contains(name)
    }

    override fun hasPendingEdits(name: String): Boolean {
        shell.exec()
    }
}

