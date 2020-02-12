package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherLifecycle {
  fun <T> withCoroutineDispatcher(f: (CoroutineDispatcher) -> T): T
}
