package com.hoang.hoangtourapi.domain.station

import com.hoang.hoangtourapi.domain.station.model.Station
import com.hoang.hoangtourapi.domain.station.model.StationDto
import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy

@Mapper(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
)
interface StationMapper {
    fun toDto(station: Station): StationDto
}
