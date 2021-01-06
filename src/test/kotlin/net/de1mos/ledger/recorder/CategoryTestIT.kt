package net.de1mos.ledger.recorder

import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.CategorySaveDto
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.BodyInserters
import java.util.*

class CategoryTestIT : RecorderApplicationTests() {

    @Test
    fun testCreateAndRead() {
        val categoryName = "vvv"
        val categoryUUID = UUID.randomUUID()
        webTestClient.put().uri("$CATEGORY_URI/$categoryUUID")
            .body(BodyInserters.fromValue(CategorySaveDto().name(categoryName)))
            .exchange()
            .expectStatus().isNoContent
            .returnResult(Void::class.java)
            .responseBody
            .blockFirst()

        val categories = webTestClient.get().uri(CATEGORY_URI).exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("X-Total", "1")
            .returnResult(CategoryDto::class.java)
            .responseBody
            .collectList().block()
        assertThat(categories, hasSize(1))
        assertThat(categories!![0].name, `is`(categoryName))
        assertThat(categories[0].id, `is`(categoryUUID))
    }
}