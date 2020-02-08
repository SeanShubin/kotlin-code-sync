package com.seanshubin.code.sync.shell

import java.nio.file.Path

data class ShellCommand(val directory: Path, val command: List<String>)
