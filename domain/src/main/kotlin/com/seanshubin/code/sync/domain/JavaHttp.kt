package com.seanshubin.code.sync.domain

import java.io.InputStream
import java.lang.RuntimeException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class JavaHttp(private val httpClient: HttpClient,
               private val requestEvent:(HttpRequest) -> Unit):Http {
  private val inputStreamHandler = HttpResponse.BodyHandlers.ofInputStream()

  override fun getInputStream(uriString: String): InputStream {
    val uri = URI.create(uriString)
    val request = HttpRequest.newBuilder().GET().uri(uri).build()
    requestEvent(request)
    val response = httpClient.send(request, inputStreamHandler)
    response.assertSuccess()
    return response.body()
  }

  private fun <T> HttpResponse<T>.assertSuccess(){
    if(statusCode() != 200){
      throw RuntimeException("For ${request().method()} '${request().uri()}', expected status code of 200, got ${statusCode()}")
    }
  }
}