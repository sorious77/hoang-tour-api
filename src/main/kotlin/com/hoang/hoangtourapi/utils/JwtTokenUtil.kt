package com.hoang.hoangtourapi.utils

import com.hoang.hoangtourapi.domain.member.auth.MemberDetails
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
    @Value("\${jwt.accessSecretKey}")
    private val accessSecretKey: String,
    @Value("\${jwt.refreshSecretKey}")
    private val refreshSecretKey: String,
    @Value("\${jwt.accessExpiration}")
    private val accessExpiration: Long,
    @Value("\${jwt.refreshExpiration}")
    private val refreshExpiration: Long,
) {
    private lateinit var accessKey: SecretKey
    private lateinit var refreshKey: SecretKey

    @PostConstruct
    fun init() {
        val accessKeyBytes = Base64.getDecoder().decode(accessSecretKey)
        val refreshKeyBytes = Base64.getDecoder().decode(refreshSecretKey)
        accessKey = Keys.hmacShaKeyFor(accessKeyBytes)
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes)
    }

    fun generateAccessToken(userDetails: MemberDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .claim("nickname", userDetails.getNickname())
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + accessExpiration))
            .signWith(accessKey)
            .compact()
    }

    fun generateRefreshToken(userDetails: MemberDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .claim("nickname", userDetails.getNickname())
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(refreshKey)
            .compact()
    }

    // 토큰 검증
    fun validateToken(
        token: String,
        isAccessToken: Boolean,
    ): Boolean {
        return validateToken(token, if (isAccessToken) accessKey else refreshKey)
    }

    // 토큰 검증
    private fun validateToken(
        token: String,
        key: SecretKey,
    ): Boolean {
        try {
            val claims = getClaimsFromToken(token, key)
            return !claims.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    // 토큰에서 이메일 추출
    fun getEmailFromToken(
        token: String,
        isAccessToken: Boolean,
    ): String {
        val claims =
            getClaimsFromToken(
                token,
                if (isAccessToken) accessKey else refreshKey,
            )
        return claims.subject
    }

    // 토큰에서 클레임 추출
    private fun getClaimsFromToken(
        token: String,
        key: SecretKey,
    ): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
