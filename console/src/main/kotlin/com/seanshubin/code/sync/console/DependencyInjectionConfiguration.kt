package com.seanshubin.code.sync.console

import com.seanshubin.code.sync.domain.Configuration
import com.seanshubin.code.sync.domain.RunWithConfiguration
import kotlinx.coroutines.CoroutineDispatcher

class DependencyInjectionConfiguration(configuration: Configuration) {
    private val createRunner: (Configuration, CoroutineDispatcher) -> Runnable = { configuration, coroutineDispatcher ->
        DependencyInjectionCoroutineDispatcher(configuration, coroutineDispatcher).runner
    }
    val runner: Runnable = RunWithConfiguration(createRunner, configuration)
}
