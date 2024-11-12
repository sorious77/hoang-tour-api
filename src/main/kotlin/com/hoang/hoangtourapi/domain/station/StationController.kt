package com.hoang.hoangtourapi.domain.station

import com.hoang.hoangtourapi.domain.station.model.StationDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stations")
class StationController(
    private val stationService: StationService,
) {
    @GetMapping("/lines")
    fun getStationListByLine(
        @RequestParam lineId: Long?,
    ): List<StationDto>? {
        return stationService.getStationListByLine(lineId)
    }
}
