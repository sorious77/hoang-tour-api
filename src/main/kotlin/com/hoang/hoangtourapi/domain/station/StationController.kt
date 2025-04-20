package com.hoang.hoangtourapi.domain.station

import com.hoang.hoangtourapi.domain.station.model.StationDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stations")
@Tag(name = "Station", description = "역 API")
class StationController(
    private val stationService: StationService,
) {
    @GetMapping("/lines")
    @Operation(
        summary = "호선별 역 조회",
        description = "해당 호선에 속해있는 모든 지하철 역을 조회합니다.",
    )
    fun getStationListByLine(
        @RequestParam lineId: Long?,
    ): List<StationDto>? {
        return if (lineId == null || lineId == 0L) {
            stationService.findEntireStationList()
        } else {
            stationService.findStationListByLine(lineId)
        }
    }
}
