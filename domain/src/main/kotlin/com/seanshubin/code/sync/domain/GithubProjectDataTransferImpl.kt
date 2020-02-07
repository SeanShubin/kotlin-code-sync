package com.seanshubin.code.sync.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import java.io.InputStream

class GithubProjectDataTransferImpl(private val objectMapper: ObjectMapper) :GithubProjectDataTransfer{
  override fun fromInputStream(inputStream: InputStream): List<GithubProject> {
    val collectionType: CollectionType = objectMapper.typeFactory.constructCollectionType(List::class.java, GithubProject::class.java)
    return objectMapper.readValue(inputStream, collectionType)
  }
}
