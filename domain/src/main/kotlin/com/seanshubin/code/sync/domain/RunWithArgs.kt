package com.seanshubin.code.sync.domain

class RunWithArgs(private val args:Array<String>,
                  private val configurationLoader: ConfigurationLoader,
                  private val createRunner:(Configuration) -> Runnable) :Runnable {
  override fun run() {
    val configuration = configurationLoader.fromArguments(args)
    val runner = createRunner(configuration)
    runner.run()
  }
}
