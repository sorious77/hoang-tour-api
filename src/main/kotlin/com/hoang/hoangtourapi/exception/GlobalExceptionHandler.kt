package com.hoang.hoangtourapi.exception

import com.hoang.hoangtourapi.common.BaseResponse
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(
        BaseException::class,
    )
    fun handleBaseException(e: BaseException): BaseResponse<Any> {
        val message =
            when (e.status) {
                BaseResponseStatus.ALREADY_EXISTS -> "이미 존재하는 ${e.message}입니다."
                else -> e.status.description
            }

        return BaseResponse(
            e.status.code,
            message,
            null,
        )
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        EntityNotFoundException::class,
        BadCredentialsException::class,
        Exception::class,
    )
    fun handleException(e: Exception): BaseResponse<Any> {
        val status =
            when (e) {
                is MethodArgumentNotValidException -> BaseResponseStatus.INVALID_INPUT
                is EntityNotFoundException -> BaseResponseStatus.ENTITY_NOT_FOUND
                is BadCredentialsException -> BaseResponseStatus.SIGN_IN_FAIL
                else -> BaseResponseStatus.SERVER_ERROR
            }

        val customMessage =
            when (e) {
                is MethodArgumentNotValidException ->
                    e.bindingResult.fieldErrors.joinToString(" ") {
                        "[${it.field} - ${it.defaultMessage ?: ""}]"
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
