package com.hoang.hoangtourapi.domain.stationline

import com.hoang.hoangtourapi.domain.stationline.model.StationLine
import org.springframework.stereotype.Service

@Service
class StationLineService(
    private val stationLineRepository: StationLineRepository,
) {
    // lineId에 해당하는 지하철 정보를 반환,
    // lineId가 null일 경우, 전체 지하철을 반환
    fun getStationLineListByLineId(lineId: Long?): List<StationLine> {
        return if (lineId == null) {
            stationLineRepository.findAll()
        } else {
            stationLineRepository.findStationLineByLineId(lineId)
        }
    }

    fun getStationLineListByStationIdList(stationIdList: List<Long>): List<StationLine> {
        return stationLineRepository.findStationLineByStationIdIn(stationIdList)
    }
}
