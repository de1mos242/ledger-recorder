package net.de1mos.ledger.recorder.controllers

import net.de1mos.ledger.recorder.api.RecordsApi
import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.Record
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*

@RestController
class RecordsControllerImpl : RecordsApi {
    override fun getRecords(exchange: ServerWebExchange?): Mono<ResponseEntity<Flux<Record>>> {
        return Mono.just(
            ResponseEntity.ok(
                Flux.just(
                    Record()
                        .id(UUID.randomUUID())
                        .category(CategoryDto().id(UUID.randomUUID()).name("valenky"))
                        .name("super v")
                        .value(BigDecimal.TEN)
                )
            )
        )
    }
}