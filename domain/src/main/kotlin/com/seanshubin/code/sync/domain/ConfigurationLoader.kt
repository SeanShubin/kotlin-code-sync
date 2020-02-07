package com.seanshubin.code.sync.domain

interface ConfigurationLoader {
  fun fromArguments(args: Array<String>): Configuration
}
