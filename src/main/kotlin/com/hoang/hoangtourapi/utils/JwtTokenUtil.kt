package com.hoang.hoangtourapi.utils

import com.hoang.hoangtourapi.domain.member.model.MemberDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    @Value("\${jwt.secretKey}")
    private val secretKey: String,
) {
    private lateinit var key: SecretKey
    private val expirationTime = 60 * 60 * 1000L

    @PostConstruct
    fun init() {
        val bytes = Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(bytes) // key값에 우리가 사용할 secret 값이 담겨진다.
    }

    fun generateToken(userDetails: MemberDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .claim("nickname", userDetails.getNickname())
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims = getClaimsFromToken(token)
            return !claims.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    fun getUsernameFromToken(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims.subject
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
