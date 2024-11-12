package com.hoang.hoangtourapi.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "INS_DATE", updatable = false)
    var insDate: LocalDateTime? = LocalDateTime.now()

    @Column(name = "INS_OPRT", nullable = false)
    var insOprt: String = ""

    @LastModifiedDate
    @Column(name = "UPD_DATE", nullable = false)
    var updDate: LocalDateTime? = LocalDateTime.now()

    @Column(name = "UPD_OPRT", nullable = false)
    var updOprt: String = ""
}
