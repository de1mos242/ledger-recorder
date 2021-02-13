package net.de1mos.ledger.recorder.repositories

import net.de1mos.ledger.recorder.models.UserToAccountLink
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono
import java.util.*

interface UserToAccountLinkRepository: ReactiveSortingRepository<UserToAccountLink, Long> {

    fun findFirstByAccountIdAndUserId(accountId: UUID, userId: UUID): UserToAccountLink?
}