package net.de1mos.ledger.recorder

import net.de1mos.ledger.recorder.services.AccountingProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(AccountingProcessor::class)
class RecorderApplication

fun main(args: Array<String>) {
    runApplication<RecorderApplication>(*args)
}
