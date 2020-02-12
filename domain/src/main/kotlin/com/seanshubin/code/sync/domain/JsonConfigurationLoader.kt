package com.seanshubin.code.sync.domain

import com.seanshubin.code.sync.contract.FilesContract
import java.nio.file.Path
import java.nio.file.Paths

class JsonConfigurationLoader(private val files: FilesContract,
                              private val configurationDataTransfer: ConfigurationDataTransfer) : ConfigurationLoader {
  override fun fromArguments(args: Array<String>): Configuration {
    val configFileName = args.getOrNull(0) ?: "local-config/code-sync-config.json"
    return fromFileNamed(configFileName, Configuration.default)
  }

  private fun fromFileNamed(fileName:String, default: Configuration): Configuration {
    val path = Paths.get(fileName)
    return fromPath(path, default)
  }

  private fun fromPath(path: Path, default: Configuration): Configuration {
    if(!files.exists(path)){
      if (!files.exists(path.parent)) {
        files.createDirectories(path.parent)
      }
      files.newOutputStream(path).use {
        configurationDataTransfer.toOutputStream(default, it)
      }
    }
    val inputStream = files.newInputStream(path)
    return configurationDataTransfer.fromInputStream(inputStream)
  }
}
