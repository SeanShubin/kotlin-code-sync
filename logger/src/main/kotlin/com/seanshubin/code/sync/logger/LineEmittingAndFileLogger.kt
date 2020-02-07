package com.seanshubin.code.sync.logger

import com.seanshubin.code.sync.contract.FilesContract
import java.nio.file.Path

class LineEmittingAndFileLogger(
    initialize: () -> Unit,
    emit: (String) -> Unit,
    files: FilesContract,
    logFile: Path
) : Logger {
    private val delegate = CompositeLogger(
            LineEmittingLogger(emit),
            FileLogger(initialize, files, logFile)
    )

    override fun log(lines: List<String>) {
        delegate.log(lines)
    }
}
