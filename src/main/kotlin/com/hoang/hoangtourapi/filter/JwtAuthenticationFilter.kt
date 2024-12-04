package com.hoang.hoangtourapi.filter

import com.hoang.hoangtourapi.domain.member.auth.MemberDetailsService
import com.hoang.hoangtourapi.utils.JwtTokenUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val memberDetailsService: MemberDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
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
        }

        filterChain.doFilter(request, response)
    }
}
