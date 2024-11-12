package com.hoang.hoangtourapi.repository

import com.hoang.hoangtourapi.model.entity.StationLine
import org.springframework.data.jpa.repository.JpaRepository

interface StationLineRepository : JpaRepository<StationLine, Long> {
    fun findStationLineByLineId(lineId: Long): List<StationLine>
    fun findStationLineByStationIdIn(stationIdList: List<Long>): List<StationLine>
}