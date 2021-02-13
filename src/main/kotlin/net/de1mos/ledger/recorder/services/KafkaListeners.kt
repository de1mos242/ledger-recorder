package net.de1mos.ledger.recorder.services

import com.fasterxml.jackson.annotation.JsonProperty
import io.debezium.serde.DebeziumSerdes
import net.de1mos.ledger.recorder.models.UserToAccountLink
import net.de1mos.ledger.recorder.repositories.UserToAccountLinkRepository
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.utils.Bytes
import org.springframework.beans.factory.InitializingBean
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Service
import java.util.*

@Service
class KafkaListeners(
    private val userToAccountLinkRepository: UserToAccountLinkRepository
) : InitializingBean {
    private lateinit var debeziumDeserializer: Deserializer<A2UEventWrapper>

//    @Bean
//    fun process(): Function<Flux<Any>, Mono<Void>> =
//        Function { flux -> flux.doOnNext {
//            println(it)
//        }.then() }

    data class A2UEventWrapper(val before: A2UEvent? = null, val after: A2UEvent? = null)
    data class A2UEvent(
        @JsonProperty("user_uuid") val userUUID: UUID? = null,
        @JsonProperty("account_uuid") val accountUUID: UUID? = null
    )

    @StreamListener(target = AccountingProcessor.DEBUG_INPUT)
    fun process(v: Message<Bytes>) {
        val data = debeziumDeserializer.deserialize(null, v.payload.get())
        println("-------------------------- v: $v and data: $data")

        if (data.before != null && data.after == null) {
            val link = userToAccountLinkRepository.findFirstByAccountIdAndUserId(
                data.before.accountUUID!!,
                data.before.userUUID!!
            )
            if (link != null) {
                userToAccountLinkRepository.delete(link)
            }
        } else if (data.before == null && data.after != null) {
            userToAccountLinkRepository.save(UserToAccountLink(data.after.accountUUID!!, data.after.userUUID!!)).block()
        }
    }

    override fun afterPropertiesSet() {
        debeziumDeserializer = DebeziumSerdes
            .payloadJson(A2UEventWrapper::class.java)
            .also { it.configure(mapOf("unknown.properties.ignored" to true), false) }
            .deserializer()
    }
}