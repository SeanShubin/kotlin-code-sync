package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.ShellCommand

interface CommandGenerator {
    fun cloneFromGithubToLocal(name: String): List<ShellCommand>
    fun addLocalToGithub(name: String): List<ShellCommand>
}