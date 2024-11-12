package com.hoang.hoangtourapi.domain.line

import com.hoang.hoangtourapi.domain.line.model.Line
import com.hoang.hoangtourapi.domain.line.model.LineRes
import org.springframework.stereotype.Service

@Service
class LineService(
    private val lineRepository: LineRepository,
    private val lineMapper: LineMapper,
) {
    // 호선 목록 반환
    fun getLineList(): List<LineRes> {
        val lineList = lineRepository.findAll()

        return lineList.map { lineMapper.toRes(it) }
    }

    fun getLineListByLineIdList(lineIdList: List<Long>): List<Line> {
        return lineRepository.findLineByLineIdIn(lineIdList)
    }
}
