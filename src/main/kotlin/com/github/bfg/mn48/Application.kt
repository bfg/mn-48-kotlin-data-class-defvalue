package com.github.bfg.mn48

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.context.annotation.Factory
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton

private val log = KotlinLogging.logger {}

@Factory
@Singleton
class Application {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Micronaut.run(*args)
    }
  }
}
