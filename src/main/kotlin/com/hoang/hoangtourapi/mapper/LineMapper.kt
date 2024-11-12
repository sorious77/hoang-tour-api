package com.hoang.hoangtourapi.mapper

import com.hoang.hoangtourapi.model.dto.LineRes
import com.hoang.hoangtourapi.model.entity.Line
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring"
)
interface LineMapper {
    fun toRes(line: Line): LineRes
}