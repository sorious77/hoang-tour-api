package com.hoang.hoangtourapi.service

import com.hoang.hoangtourapi.repository.LineRepository
import com.hoang.hoangtourapi.repository.StationRepository
import org.springframework.stereotype.Service

@Service
class StationService(
    private val stationRepository: StationRepository,
    private val lineRepository: LineRepository
) {

}