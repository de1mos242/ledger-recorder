package net.de1mos.ledger.recorder.controllers

import net.de1mos.ledger.recorder.api.RecordsApi
import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.Record
import net.de1mos.ledger.recorder.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.security.Principal
import java.util.*

@RestController
class RecordsControllerImpl(private val userService: UserService) : RecordsApi {
    override fun getRecords(exchange: ServerWebExchange): Mono<ResponseEntity<Flux<Record>>> {

        return userService.getUserId(exchange)
            .flatMap { username ->
                Mono.just(
                    ResponseEntity.ok(
                        Flux.just(
                            Record()
                                .id(UUID.randomUUID())
                                .category(CategoryDto().id(UUID.randomUUID()).name("valenky"))
                                .name("super v $username")
                                .value(BigDecimal.TEN)
                        )
                    )
                )
            }
    }
}