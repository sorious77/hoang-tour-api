package com.hoang.hoangtourapi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "STATION_LINES")
data class StationLine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_LINE_ID", nullable = false)
    var stationLineId: Long? = 0L,
    @Column(name = "STATION_ID", nullable = false)
    var stationId: Long,
    @Column(name = "LINE_ID", nullable = false)
    var lineId: Long
)