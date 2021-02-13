package net.de1mos.ledger.recorder

import net.de1mos.ledger.recorder.api.models.CategoryDto
import net.de1mos.ledger.recorder.api.models.CategorySaveDto
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt
import org.springframework.web.reactive.function.BodyInserters
import java.util.*

class CategoryTestIT : RecorderApplicationTests() {

    @Test
    @Disabled
    fun testCreateAndRead() {
        val categoryName = "vvv"
        val categoryUUID = UUID.randomUUID()
        webTestClient
            .mutateWith(mockJwt())
            .put().uri("$categoryUrl/$categoryUUID")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(CategorySaveDto().name(categoryName)))
            .exchange()
            .expectStatus().isNoContent
            .returnResult(Void::class.java)
            .responseBody
            .blockFirst()

        val categories = webTestClient
            .mutateWith(mockJwt())
            .get().uri(categoryUrl)
            .exchange()
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