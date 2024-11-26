package com.hoang.hoangtourapi.exception

import com.hoang.hoangtourapi.enums.BaseResponseStatus

class BaseException(
    val status: BaseResponseStatus,
    override val message: String? = "",
) : RuntimeException()
