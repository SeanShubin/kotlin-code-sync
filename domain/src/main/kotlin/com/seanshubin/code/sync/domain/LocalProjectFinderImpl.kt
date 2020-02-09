package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.contract.FilesContract
import java.nio.file.Path
import kotlin.streams.toList

class LocalProjectFinderImpl(private val localGithubPath: Path,
                             private val files: FilesContract,
                             private val ignored: List<String>) : LocalProjectFinder {
    override fun findAll(): List<String> {
        val ignored = { candidate: String -> ignored.contains(candidate) }
        val pathToFileName = { path: Path -> path.fileName.toString() }
        return files.list(localGithubPath).toList().map(pathToFileName).filterNot(ignored)
    }
}
