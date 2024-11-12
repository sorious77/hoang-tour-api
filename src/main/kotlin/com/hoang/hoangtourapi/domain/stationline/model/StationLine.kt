package com.hoang.hoangtourapi.domain.stationline.model

import com.hoang.hoangtourapi.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "STATION_LINES")
data class StationLine(
    @Id
    @Column(name = "STATION_LINE_ID", nullable = false)
    val stationLineId: Long,
    @Column(name = "STATION_ID", nullable = false)
    var stationId: Long,
    @Column(name = "LINE_ID", nullable = false)
    var lineId: Long,
) : BaseEntity()
