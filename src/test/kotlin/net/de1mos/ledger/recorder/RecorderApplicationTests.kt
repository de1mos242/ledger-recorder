package net.de1mos.ledger.recorder

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureWebTestClient
class RecorderApplicationTests {

    protected val CATEGORY_URI = "/api/recorder/categories"

    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Test
    fun contextLoads() {
    }

}
