package com.hoang.hoangtourapi.filter

import com.hoang.hoangtourapi.domain.member.auth.MemberDetailsService
import com.hoang.hoangtourapi.utils.JwtTokenUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val memberDetailsService: MemberDetailsService,
) : OncePerRequestFilter() {
    companion object {
        private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val path = request.servletPath
        // 인증 예외 경로 처리
        if (path.startsWith("/swagger-ui") ||
            path.startsWith("/api-docs") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/api/v1/members") ||
            path.startsWith("/health")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        val header = request.getHeader("Authorization")
        val token = header?.takeIf { it.startsWith("Bearer ") }?.removePrefix("Bearer ")

        if (token != null && jwtTokenUtil.validateToken(token)) {
            val email = jwtTokenUtil.getEmail(token)
            val memberDetails = memberDetailsService.loadUserByUsername(email)

            val authentication =
                UsernamePasswordAuthenticationToken(
                    memberDetails,
                    null,
                    memberDetails.authorities,
                )

            SecurityContextHolder.getContext().authentication = authentication
        } else {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write("Forbidden: Invalid or missing token")
            return
        }

        val startTime = System.currentTimeMillis()
        filterChain.doFilter(request, response)
        val endTime = System.currentTimeMillis()

        val url = request.requestURI
        val queryString = if (request.queryString.isNullOrEmpty()) "" else "?${request.queryString}"

        log.info("${request.method.uppercase()} $url$queryString ${endTime - startTime}ms")
    }
}
