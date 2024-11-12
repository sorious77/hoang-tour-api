package com.hoang.hoangtourapi.domain.line.model

data class LineReq(
    val lineName: String,
    val lineEngName: String,
)

data class LineRes(
    val lineId: Long,
    val lineName: String,
)
