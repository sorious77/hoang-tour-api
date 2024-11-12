package com.hoang.hoangtourapi.domain.station

import com.hoang.hoangtourapi.domain.station.model.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Long> {
    fun findStationByStationIdIn(stationIdList: List<Long>): List<Station>
}
