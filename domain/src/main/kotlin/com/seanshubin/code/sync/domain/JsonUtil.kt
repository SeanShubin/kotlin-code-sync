package com.seanshubin.code.sync.domain

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Path

object JsonUtil {
  private val module = SimpleModule()
      .addSerializer(Path::class.java, ToStringSerializer())
  val objectMapper: ObjectMapper =
      ObjectMapper()
          .registerModule(KotlinModule())
          .enable(SerializationFeature.INDENT_OUTPUT)
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .registerModule(module)
}