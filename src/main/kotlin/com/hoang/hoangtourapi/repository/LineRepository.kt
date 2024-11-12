package com.hoang.hoangtourapi.repository

import com.hoang.hoangtourapi.model.entity.Line
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LineRepository : JpaRepository<Line, Long> {
    fun findLineByLineIdIn(lindIdList: List<Long>): List<Line>
}