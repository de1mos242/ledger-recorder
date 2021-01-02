package net.de1mos.ledger.recorder.controllers

import net.de1mos.ledger.recorder.api.RecordsApi
import net.de1mos.ledger.recorder.api.models.Category
import net.de1mos.ledger.recorder.api.models.Record
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

@RestController
class RecordsControllerImpl : RecordsApi {
    override fun recordsGet(exchange: ServerWebExchange?): Mono<ResponseEntity<Flux<Record>>> {
//        return exchange.response.writeWith(Mono.just(Flux.just(Record().id("dd").category(Category().id("c1").name("valenky")).name("super v").value(
//            BigDecimal.TEN))))
        return Mono.just(
            ResponseEntity.ok(
                Flux.just(
                    Record()
                        .id("dd")
                        .category(Category().id("c1").name("valenky"))
                        .name("super v")
                        .value(BigDecimal.TEN)
                )
            )
        )
    }
}