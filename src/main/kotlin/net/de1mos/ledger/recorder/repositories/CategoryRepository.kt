package net.de1mos.ledger.recorder.repositories

import net.de1mos.ledger.recorder.models.Category
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface CategoryRepository : ReactiveSortingRepository<Category, Long> {
    fun findCategoryByExternalUUID(externalUUID: UUID): Mono<Category>

    fun findAllByNameStartsWithOrderByName(name: String, pageable: Pageable): Flux<Category>
}