package com.seanshubin.code.sync.domain

import java.io.InputStream

interface Http {
  fun getInputStream(uriString:String):InputStream
}
