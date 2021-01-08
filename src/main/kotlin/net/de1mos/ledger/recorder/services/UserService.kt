package net.de1mos.ledger.recorder.services

import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.Principal

@Service
class UserService {

    fun getUserId(exchange: ServerWebExchange): Mono<String> {
        return exchange.getPrincipal<Principal>()
            .switchIfEmpty(Mono.error(IllegalStateException("Principal is empty")))
            .map { it.name }
    }
}