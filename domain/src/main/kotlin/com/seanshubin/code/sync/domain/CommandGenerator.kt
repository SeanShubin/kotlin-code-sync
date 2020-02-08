package com.seanshubin.code.sync.domain

interface CommandGenerator {
    fun cloneFromGithubToLocal(name: String): List<String>
    fun addLocalToGithub(name: String): List<String>
}