package com.hoang.hoangtourapi.common

import com.fasterxml.jackson.annotation.JsonInclude

data class BaseResponse<T>(
    val code: Int,
    val description: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T?,
)
