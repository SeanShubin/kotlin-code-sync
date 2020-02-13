package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class RunWithConfiguration(private val createRunner: (Configuration, CoroutineDispatcher) -> Runnable,
                           private val configuration: Configuration) : Runnable {
  override fun run() {
      val threadPool = Executors.newCachedThreadPool()
      try {
          val coroutineDispatcher: CoroutineDispatcher = threadPool.asCoroutineDispatcher()
          val runner = createRunner(configuration, coroutineDispatcher)
          runner.run()
      } finally {
          threadPool.shutdown()
      }
  }
}
