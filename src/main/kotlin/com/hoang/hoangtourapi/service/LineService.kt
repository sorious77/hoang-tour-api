package com.hoang.hoangtourapi.service

import com.hoang.hoangtourapi.mapper.LineMapper
import com.hoang.hoangtourapi.model.dto.LineRes
import com.hoang.hoangtourapi.model.entity.Line
import com.hoang.hoangtourapi.repository.LineRepository
import org.springframework.stereotype.Service

@Service
class LineService(
    private val lineRepository: LineRepository,
    private val lineMapper: LineMapper
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