package com.hoang.hoangtourapi.model.dto

data class StationDto(
    val stationId: Long,
    val stationName: String,
    val stationEngName: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    var lineNameList: List<String>?,
    var lineColor: String?
)

data class LineReq(
    val lineName: String,
    val lineEngName: String,
)