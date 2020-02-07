package com.seanshubin.code.sync.domain

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream
import java.io.OutputStream

class ConfigurationDataTransferImpl(private val objectMapper: ObjectMapper) : ConfigurationDataTransfer {
  override fun fromInputStream(inputStream: InputStream): Configuration {
    return objectMapper.readValue(inputStream, Configuration::class.java)
  }

  override fun toOutputStream(configuration: Configuration, outputStream: OutputStream) {
    objectMapper.writeValue(outputStream, configuration)
  }
}
