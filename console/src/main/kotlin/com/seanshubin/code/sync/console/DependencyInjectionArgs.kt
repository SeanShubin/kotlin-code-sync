package com.seanshubin.code.sync.console

import com.fasterxml.jackson.databind.ObjectMapper
import com.seanshubin.code.sync.contract.FilesContract
import com.seanshubin.code.sync.contract.FilesDelegate
import com.seanshubin.code.sync.domain.*

class DependencyInjectionArgs(args:Array<String>) {
  private val objectMapper:ObjectMapper = JsonUtil.objectMapper
  private val files: FilesContract = FilesDelegate
  private val configurationDataTransfer:ConfigurationDataTransfer = ConfigurationDataTransferImpl(objectMapper)
  private val configurationLoader:ConfigurationLoader = JsonConfigurationLoader(files, configurationDataTransfer)
  private val createRunner:(Configuration) -> Runnable = { configuration ->
    DependencyInjectionConfiguration(configuration).runner
  }
  val runner:Runnable = RunWithArgs(args, configurationLoader, createRunner)
}
