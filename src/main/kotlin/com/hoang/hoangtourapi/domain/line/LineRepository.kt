package com.hoang.hoangtourapi.domain.line

import com.hoang.hoangtourapi.domain.line.model.Line
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LineRepository : JpaRepository<Line, Long> {
    fun findLineByLineIdIn(lindIdList: List<Long>): List<Line>
}
