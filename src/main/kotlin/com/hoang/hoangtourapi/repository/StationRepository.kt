package com.hoang.hoangtourapi.repository

import com.hoang.hoangtourapi.model.entity.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Long> {
    fun findStationByStationIdIn(stationIdList: List<Long>): List<Station>
}