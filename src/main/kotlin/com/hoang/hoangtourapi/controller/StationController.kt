package com.hoang.hoangtourapi.controller

import com.hoang.hoangtourapi.mapper.StationMapper
import com.hoang.hoangtourapi.model.dto.LineReq
import com.hoang.hoangtourapi.model.entity.Station
import com.hoang.hoangtourapi.repository.LineRepository
import com.hoang.hoangtourapi.repository.StationRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stations")
class StationController(
    private val stationRepository: StationRepository,
    private val lineRepository: LineRepository,
    private val stationMapper: StationMapper
) {
    @PostMapping
    fun insertStations(@RequestBody stations: List<Station>) {
        stations.forEach {station ->
            stationRepository.save(station)
        }
    }

    @PostMapping("/lines")
    fun insertLines(@RequestBody lines: List<LineReq>) {
        lines.forEach {
            println(it)
            val line = stationMapper.toEntity(it)
            println(line)
            lineRepository.save(line)
        }
    }
}