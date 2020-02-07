package com.seanshubin.code.sync.logger

class LineEmittingLogger(private val emit: (String) -> Unit) : Logger {
    override fun log(lines: List<String>) {
        lines.forEach(emit)
    }
}