package com.seanshubin.code.sync.shell

class LineConsumer : (String) -> Unit {
  val lines = mutableListOf<String>()
  override fun invoke(line: String) {
    lines.add(line)
  }
}