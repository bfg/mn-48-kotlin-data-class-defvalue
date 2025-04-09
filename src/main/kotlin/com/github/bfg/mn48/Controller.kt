package com.github.bfg.mn48

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

private val log = KotlinLogging.logger {}

@Serdeable
@ConfigurationProperties("app.controller")
data class ControllerCfg(
    val enabled: Boolean = true,
    @NotBlank val apiBase:String = "https://api.example.com/",
    @NotBlank val something: String = "some-value"
)

@Controller("/v1/my-controller")
@Produces(MediaType.APPLICATION_JSON)
class Controller(
    private val cfg: ControllerCfg,
) {

  @Get("/")
  fun get(req: HttpRequest<*>): ControllerCfg {
    return cfg
  }
}
