package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class CoroutinesDispatcherLifecycleImpl : CoroutineDispatcherLifecycle {
  override fun <T> withCoroutineDispatcher(f: (CoroutineDispatcher) -> T): T {
    val threadPool = Executors.newCachedThreadPool()
    val coroutineDispatcher: CoroutineDispatcher = threadPool.asCoroutineDispatcher()
    val result = f(coroutineDispatcher)
    threadPool.shutdown()
    return result
  }
}