package com.hoang.hoangtourapi.domain.line

import com.hoang.hoangtourapi.domain.line.model.Line
import com.hoang.hoangtourapi.domain.line.model.LineReq
import com.hoang.hoangtourapi.domain.line.model.LineRes
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(
    componentModel = "spring",
)
interface LineMapper {
    @Mappings(
        Mapping(target = "insOprt", constant = "SYSTEM"),
        Mapping(target = "updOprt", constant = "SYSTEM"),
    )
    fun toEntity(lineReq: LineReq): Line

    fun toRes(line: Line): LineRes
}
