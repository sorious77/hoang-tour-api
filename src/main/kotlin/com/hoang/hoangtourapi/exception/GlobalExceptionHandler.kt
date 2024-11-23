package com.hoang.hoangtourapi.exception

import com.hoang.hoangtourapi.common.BaseResponse
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(
        AlreadyExistsException::class,
        MethodArgumentNotValidException::class,
        PasswordMismatchException::class,
        Exception::class,
    )
    fun handleCustomException(e: Exception): BaseResponse<Any> {
        val status =
            when (e) {
                is AlreadyExistsException -> BaseResponseStatus.ALREADY_EXISTS
                is MethodArgumentNotValidException -> BaseResponseStatus.INVALID_INPUT
                is PasswordMismatchException -> BaseResponseStatus.PASSWORD_MISMATCH
                else -> BaseResponseStatus.SERVER_ERROR
            }

        val customMessage =
            when (e) {
                is AlreadyExistsException -> "이미 존재하는 ${e.field}입니다."

                is MethodArgumentNotValidException -> {
                    e.bindingResult.fieldErrors.joinToString(" ") {
                        "[${it.field} - ${it.defaultMessage ?: ""}]"
                    }
                }

                else -> ""
            }

        return BaseResponse(
            status.code,
            customMessage.ifEmpty { status.description },
            null,
        )
    }
}
