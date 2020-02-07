package com.seanshubin.code.sync.domain

import java.io.InputStream
import java.io.OutputStream

interface ConfigurationDataTransfer{
  fun fromInputStream(inputStream: InputStream):Configuration
  fun toOutputStream(configuration: Configuration, outputStream:OutputStream)
}
