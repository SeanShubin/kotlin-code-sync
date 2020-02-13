package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.shell.Shell
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class RunWithAsync(private val githubProjectFinder: GithubProjectFinder,
                   private val localProjectFinder: LocalProjectFinder,
                   private val commandGenerator: CommandGenerator,
                   private val shell: Shell,
                   private val projectFactory: ProjectFactory,
                   private val projectSyncedEvent: (ProjectAndStatus) -> Unit) : Runnable {
  override fun run() {
    val threadPool = Executors.newCachedThreadPool()
    val coroutineDispatcher: CoroutineDispatcher = threadPool.asCoroutineDispatcher()
    val remote = githubProjectFinder.findAll()
    val local = localProjectFinder.findAll()
    val loadProject = createLoadProjectFunction(local, remote)
    val projectNames = (remote + local).sorted().distinct()
    val projects = projectNames.map(loadProject)
    val synced = projects.map(::syncProject)
    synced.forEach(projectSyncedEvent)
//    val missing = remote - local
//    val extra = local - remote
//    val downloadCommands = missing.flatMap(commandGenerator::cloneFromGithubToLocal)
//    val uploadCommands = extra.flatMap(commandGenerator::addLocalToGithub)
//    val commands = downloadCommands + uploadCommands
//    commands.forEach { shell.exec(it) }
    threadPool.shutdown()
  }

  private fun createLoadProjectFunction(local: List<String>, remote: List<String>): (String) -> Project {
    fun loadProjectFunction(name: String): Project {
      return projectFactory.createProject(name, local, remote)
    }
    return ::loadProjectFunction
  }

  private fun syncProject(project: Project): ProjectAndStatus {
    if (!project.existsInGitlab()) {
      return ProjectAndStatus(project, ProjectStatus.NEED_TO_CREATE_IN_GITHUB)
    } else if (!project.existsLocally()) {
      project.clone()
      return ProjectAndStatus(project, ProjectStatus.IN_SYNC)
    } else if (project.hasPendingEdits()) {
      return ProjectAndStatus(project, ProjectStatus.NEED_TO_COMMIT_PENDING_EDITS)
    } else {
      project.fetch()
      if (project.hasRemoteCommits()) {
        project.rebase()
      }
      if (project.hasLocalCommits()) {
        project.push()
      }
      return ProjectAndStatus(project, ProjectStatus.IN_SYNC)
    }
  }
}
