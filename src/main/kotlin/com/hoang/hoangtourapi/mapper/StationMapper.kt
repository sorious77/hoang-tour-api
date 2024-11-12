package com.hoang.hoangtourapi.mapper

import com.hoang.hoangtourapi.model.dto.StationDto
import com.hoang.hoangtourapi.model.entity.Station
import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy

@Mapper(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
interface StationMapper {
    fun toDto(station: Station): StationDto
}