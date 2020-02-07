package com.seanshubin.code.sync.domain

import org.junit.Test
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import com.seanshubin.code.sync.domain.IoUtil.stringToInputStream
import com.seanshubin.code.sync.domain.IoUtil.inputStreamToString
import com.seanshubin.code.sync.domain.IoUtil.stringToOutputStream
import com.seanshubin.code.sync.domain.IoUtil.bytesToString
import com.seanshubin.code.sync.domain.IoUtil.bytesToOutputStream
import com.seanshubin.code.sync.domain.IoUtil.stringToReader
import com.seanshubin.code.sync.domain.IoUtil.readerToString
import com.seanshubin.code.sync.domain.IoUtil.stringToBytes

class IoUtilTest {
  private val charset = StandardCharsets.UTF_8

  @Test
  fun testBytes() {
    val inputStream = stringToInputStream("Hello, world!", charset)
    val string = inputStreamToString(inputStream, charset)
    assertEquals("Hello, world!", string)
  }

  @Test
  fun testStringToOutputStream() {
    val original = "Hello, world!"
    val outputStream = ByteArrayOutputStream()
    stringToOutputStream(original, charset, outputStream)
    val string = bytesToString(outputStream.toByteArray(), charset)
    assertEquals("Hello, world!", string)
  }

  @Test
  fun testBytesToOutputStream() {
    val original = "Hello, world!"
    val bytes = stringToBytes(original, charset)
    val outputStream = ByteArrayOutputStream()
    bytesToOutputStream(bytes, outputStream)
    val string = bytesToString(outputStream.toByteArray(), charset)
    assertEquals("Hello, world!", string)
  }

  @Test
  fun testChars() {
    val reader = stringToReader("Hello, world!")
    val string = readerToString(reader)
    assertEquals("Hello, world!", string)
  }
}
