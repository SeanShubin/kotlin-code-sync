package com.seanshubin.code.sync.domain

import java.nio.file.Paths

data class Configuration(val githubUserName: ConfigString,
                         val localGithubDirectory: ConfigPath,
                         val logDir: ConfigPath,
                         val ignoreLocalDirNames: ConfigStringList) {
    companion object {
        val default = Configuration(
                githubUserName = ConfigString(value = "UserName", description = "Your user name in github"),
                localGithubDirectory = ConfigPath(value = Paths.get("."), description = "Where you check out your github repositories"),
                logDir = ConfigPath(value = Paths.get("out/logs"), description = "Where log files will be generated"),
                ignoreLocalDirNames = ConfigStringList(value = emptyList(), description = "Local directory names to ignore"))
    }
}
