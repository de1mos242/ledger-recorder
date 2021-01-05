package net.de1mos.ledger.recorder.controllers

import net.de1mos.ledger.recorder.api.CategoriesApi
import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.CategorySaveDto
import net.de1mos.ledger.recorder.services.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
class CategoriesControllerImpl(private val categoryService: CategoryService) : CategoriesApi {
    override fun getCategories(
        page: Int,
        pageSize: Int,
        exchange: ServerWebExchange?
    ): Mono<ResponseEntity<Flux<CategoryDto>>> {
        return categoryService.getCategories(page, pageSize)
            .map {
                val (categories, total) = it
                ResponseEntity.ok().header("X-Total", total.toString()).body(categories)
            }
    }

    override fun putCategory(
        categoryId: UUID,
        categorySaveDto: Mono<CategorySaveDto>,
        exchange: ServerWebExchange?
    ): Mono<ResponseEntity<Void>> {
        return categorySaveDto.flatMap { categoryService.putCategory(categoryId, it) }
            .flatMap { Mono.just(ResponseEntity.noContent().build()) }
    }
}