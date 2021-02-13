package net.de1mos.ledger.recorder.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("user_to_account_links")
data class UserToAccountLink(
    @Column("account_id") var accountId: UUID,
    @Column("user_id") var userId: UUID,
    @Id @Column("id") var id: Long? = null
)