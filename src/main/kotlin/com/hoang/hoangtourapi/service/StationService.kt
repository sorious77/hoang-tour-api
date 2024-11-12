package com.hoang.hoangtourapi.service

import com.hoang.hoangtourapi.mapper.StationMapper
import com.hoang.hoangtourapi.repository.StationRepository
import org.springframework.stereotype.Service

@Service
class StationService(
    private val lineService: LineService,
    private val stationLineService: StationLineService,
    private val stationRepository: StationRepository,
    private val stationMapper: StationMapper
) {
    // 특정 호선에 해당하는 지하철 역 정보들을 반환
    fun getStationListByLine(lineId: Long?): Any? {
        val stationLineList = stationLineService.getStationLineListByLineId(lineId)
        if (stationLineList.isEmpty()) return null

        val stationIdList = stationLineList.map { it.stationLineId }.distinct()
        // lineId에 해당하는 모든 역 조회
        val stationList = stationRepository.findStationByStationIdIn(stationIdList)

        // stationId에 해당하는 모든 stationLineList 조회
        val stationIdToLineIdMap = stationLineService.getStationLineListByStationIdList(stationIdList)
            .groupBy({ it.stationId }, { it.lineId })

        val lineIdList = stationIdToLineIdMap.values.flatten().distinct()
        val lineMap = lineService.getLineListByLineIdList(lineIdList).associateBy { it.lineId }

        val list = stationList.map { station ->
            val associatedLines = stationIdToLineIdMap[station.stationId]
                ?.mapNotNull { lineMap[it] }

            stationMapper.toDto(station)
                .apply {
                    this.lineNameList = associatedLines?.map { it.lineName } ?: listOf()
                    this.lineColor = associatedLines?.firstOrNull()?.lineColor
                }
        }

        return list
    }
}