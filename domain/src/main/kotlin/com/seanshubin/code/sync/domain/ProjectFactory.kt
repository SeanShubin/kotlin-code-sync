package com.seanshubin.code.sync.domain

interface ProjectFactory {
    fun createProject(name: String, local: List<String>, remote: List<String>): Project
}

