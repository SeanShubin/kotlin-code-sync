package com.seanshubin.code.sync.shell

data class ShellResult(val exitCode: Int,
                       val outputLines: List<String>,
                       val errorLines: List<String>)