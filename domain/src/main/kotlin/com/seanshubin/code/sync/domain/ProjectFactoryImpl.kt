package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.Shell
import java.nio.file.Path

class ProjectFactoryImpl(private val shell: Shell,
                         private val localBasePath: Path) : ProjectFactory {
  override fun createProject(name: String, local: List<String>, remote: List<String>): Project {
    val projectApi = ProjectApiImpl(shell, localBasePath, local, remote)
    return ProjectImpl(name, projectApi)
  }
}