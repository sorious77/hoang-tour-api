package com.hoang.hoangtourapi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "`LINES`")
data class Line(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID", nullable = false)
    var lineId: Long? = 0L,
    @Column(name = "LINE_NAME", nullable = false)
    var lineName: String,
    @Column(name = "LINE_ENG_NAME", nullable = false)
    var lineEngName: String,
    @Column(name = "LINE_COLOR", nullable = true)
    var lineColor: String?,
) : BaseEntity()