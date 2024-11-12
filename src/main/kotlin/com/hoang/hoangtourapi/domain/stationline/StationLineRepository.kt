package com.hoang.hoangtourapi.domain.stationline

import com.hoang.hoangtourapi.domain.stationline.model.StationLine
import org.springframework.data.jpa.repository.JpaRepository

interface StationLineRepository : JpaRepository<StationLine, Long> {
    fun findStationLineByLineId(lineId: Long): List<StationLine>

    fun findStationLineByStationIdIn(stationIdList: List<Long>): List<StationLine>
}
