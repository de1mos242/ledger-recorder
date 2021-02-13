package net.de1mos.ledger.recorder.services

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel
import java.util.*

data class User2AccountMessage(val userUUID: UUID, val accountUUID: UUID)

const val PARTITION_KEY_NAME = "partitionKey"

interface AccountingProcessor {

    @Input(DEBUG_INPUT)
    fun listenA2UEvents(): SubscribableChannel

    companion object {
        const val DEBUG_INPUT = "process-in-0"
    }

}