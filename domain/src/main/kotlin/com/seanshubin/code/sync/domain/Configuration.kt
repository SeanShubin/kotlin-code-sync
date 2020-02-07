package com.seanshubin.code.sync.domain

import java.nio.file.Paths

data class Configuration(val githubName:ConfigString,
                         val localPath:ConfigPath,
                         val logDir:ConfigPath){
  companion object {
    val default = Configuration(
        githubName = ConfigString(value = "UserName", description = "Your user name in github"),
        localPath = ConfigPath(value = Paths.get("."), description = "Where you check out your github repositories"),
        logDir = ConfigPath(value= Paths.get("out/logs"), description = "Where log files will be generated"))
  }
}
