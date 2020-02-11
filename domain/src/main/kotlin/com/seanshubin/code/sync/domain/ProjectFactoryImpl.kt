package com.seanshubin.code.sync.domain

class ProjectFactoryImpl : ProjectFactory {
    override fun createProject(name: String, local: List<String>, remote: List<String>): Project {
        val projectApi = ProjectApiImpl(local, remote)
        return ProjectImpl(name, projectApi)
    }
}