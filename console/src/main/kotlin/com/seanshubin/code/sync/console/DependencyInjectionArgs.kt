package com.seanshubin.code.sync.console

import com.fasterxml.jackson.databind.ObjectMapper
import com.seanshubin.code.sync.contract.FilesContract
import com.seanshubin.code.sync.contract.FilesDelegate
import com.seanshubin.code.sync.domain.*

class DependencyInjectionArgs(args:Array<String>) {
  val objectMapper:ObjectMapper = JsonUtil.objectMapper
  val files: FilesContract = FilesDelegate
  val configurationDataTransfer:ConfigurationDataTransfer = ConfigurationDataTransferImpl(objectMapper)
  val configurationLoader:ConfigurationLoader = JsonConfigurationLoader(files, configurationDataTransfer)
  val createRunner:(Configuration) -> Runnable = { configuration ->
    DependencyInjectionConfiguration(configuration).runner
  }
  val runner:Runnable = RunWithArgs(args, configurationLoader, createRunner)
}
