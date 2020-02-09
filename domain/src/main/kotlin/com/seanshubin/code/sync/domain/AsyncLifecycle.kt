package com.seanshubin.code.sync.domain

import kotlinx.coroutines.CoroutineDispatcher

interface AsyncLifecycle {
    fun withCoroutineDispatcher(f: (CoroutineDispatcher) -> Unit)
}
