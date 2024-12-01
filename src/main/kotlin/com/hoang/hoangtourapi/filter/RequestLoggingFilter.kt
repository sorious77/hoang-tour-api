package com.hoang.hoangtourapi.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestLoggingFilter : OncePerRequestFilter() {
    companion object {
        private val log = LoggerFactory.getLogger(RequestLoggingFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val url = request.requestURI
        val queryString = if (request.queryString.isNullOrEmpty()) "" else "?${request.queryString}"

        val startTime = System.currentTimeMillis()
        filterChain.doFilter(request, response)
        val endTime = System.currentTimeMillis()

        log.info("${request.method.uppercase()} $url$queryString ${endTime - startTime}ms")
    }
}
