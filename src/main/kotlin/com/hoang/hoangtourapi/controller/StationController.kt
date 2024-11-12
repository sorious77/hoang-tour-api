package com.hoang.hoangtourapi.controller

import com.hoang.hoangtourapi.service.StationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stations")
class StationController(
    private val stationService: StationService
) {
    @GetMapping("/lines")
    fun getStationListByLine(@RequestParam lineId: Long?): Any? {
        return stationService.getStationListByLine(lineId)
    }
}