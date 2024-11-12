package com.hoang.hoangtourapi.mapper

import com.hoang.hoangtourapi.model.dto.LineReq
import com.hoang.hoangtourapi.model.dto.LineRes
import com.hoang.hoangtourapi.model.entity.Line
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(
    componentModel = "spring"
)
interface LineMapper {
    @Mappings(
        Mapping(target = "insOprt", constant = "SYSTEM"),
        Mapping(target = "updOprt", constant = "SYSTEM"),
    )
    fun toEntity(lineReq: LineReq): Line

    fun toRes(line: Line): LineRes
}