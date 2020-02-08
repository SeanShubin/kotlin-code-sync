package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.contract.FilesContract
import java.nio.file.Path
import kotlin.streams.toList

class LocalProjectFinderImpl(private val localGithubPath: Path,
                             private val files: FilesContract) : LocalProjectFinder {
    override fun findAll(): List<String> {
        return files.list(localGithubPath).map { it.fileName.toString() }.toList()
    }
}