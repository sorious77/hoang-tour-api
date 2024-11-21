package com.hoang.hoangtourapi.common

import com.hoang.hoangtourapi.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "INS_DATE", updatable = false)
    var insDate: LocalDateTime? = null

    @Column(name = "INS_OPRT", nullable = false)
    var insOprt: String = "SYSTEM"

    @LastModifiedDate
    @Column(name = "UPD_DATE", nullable = false)
    var updDate: LocalDateTime? = null

    @Column(name = "UPD_OPRT", nullable = false)
    var updOprt: String = "SYSTEM"

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    var status: Status = Status.ACTIVE
}
