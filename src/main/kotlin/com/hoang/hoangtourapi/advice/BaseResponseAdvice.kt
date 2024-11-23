package com.hoang.hoangtourapi.advice

import com.hoang.hoangtourapi.common.BaseResponse
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice(basePackages = ["com.hoang.hoangtourapi.domain"])
class BaseResponseAdvice : ResponseBodyAdvice<Any> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Any? {
        return when (body) {
            is BaseResponse<*> -> body

            null ->
                BaseResponse(
                    BaseResponseStatus.EMPTY_RESULT.code,
                    BaseResponseStatus.EMPTY_RESULT.description,
                    null,
                )

            else ->
                BaseResponse(
                    BaseResponseStatus.SUCCESS.code,
                    BaseResponseStatus.SUCCESS.description,
                    body,
                )
        }
    }
}
