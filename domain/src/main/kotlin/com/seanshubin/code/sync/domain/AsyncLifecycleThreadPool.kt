package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class AsyncLifecycleThreadPool : AsyncLifecycle {
    override fun withCoroutineDispatcher(f: (CoroutineDispatcher) -> Unit) {
        val executorService = Executors.newCachedThreadPool()
        val coroutineDispatcher = executorService.asCoroutineDispatcher()
        f(coroutineDispatcher)
        executorService.shutdown()
    }
}