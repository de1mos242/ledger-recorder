package net.de1mos.ledger.recorder.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("categories")
data class Category(
    @Column("name") var name: String,
    @Column("external_uuid") var externalUUID: UUID,
    @Column("user_id") var userId: String,
    @Id @Column("id") var id: Long? = null
)