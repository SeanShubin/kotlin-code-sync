package com.seanshubin.code.sync.domain

import java.io.InputStream

interface GithubProjectDataTransfer {
  fun fromInputStream(inputStream: InputStream): List<GithubProject>
}
