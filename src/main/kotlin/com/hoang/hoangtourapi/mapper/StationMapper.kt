package com.hoang.hoangtourapi.mapper

import com.hoang.hoangtourapi.model.dto.LineReq
import com.hoang.hoangtourapi.model.entity.Line
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueMappingStrategy

@Mapper(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
interface StationMapper {
    @Mappings(
        Mapping(target = "insOprt", constant = "SYSTEM"),
        Mapping(target = "updOprt", constant = "SYSTEM"),
    )
    fun toEntity(lineReq: LineReq): Line
}