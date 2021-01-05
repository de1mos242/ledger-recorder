package net.de1mos.ledger.recorder.services

import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.CategorySaveDto
import net.de1mos.ledger.recorder.models.Category
import net.de1mos.ledger.recorder.repositories.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun putCategory(categoryUUID: UUID, categorySaveDto: CategorySaveDto): Mono<Category> {
        return categoryRepository
            .findCategoryByExternalUUID(categoryUUID)
            .map { it.copy(name = categorySaveDto.name) }
            .switchIfEmpty(Mono.just(Category(categorySaveDto.name, categoryUUID)))
            .flatMap { categoryRepository.save(it) }
    }

    fun getCategories(page: Int, pageSize: Int): Mono<Pair<Flux<CategoryDto>, Long>> {
        val categories = categoryRepository.findAllByNameStartsWithOrderByName("", PageRequest.of(page, pageSize))
            .map { CategoryDto().id(it.externalUUID).name(it.name) }
        return categoryRepository.count().map { categories to it }
    }
}