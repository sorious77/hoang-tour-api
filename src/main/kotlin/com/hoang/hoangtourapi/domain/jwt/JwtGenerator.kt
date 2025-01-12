package com.hoang.hoangtourapi.domain.jwt

import com.hoang.hoangtourapi.domain.member.auth.MemberDetails
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtGenerator {
    fun generateAccessToken(
        accessSecretKey: SecretKey,
        accessExpiration: Long,
        userDetails: MemberDetails,
    ): String {
        val now = System.currentTimeMillis()

        return Jwts.builder()
            .claim("nickname", userDetails.getNickname())
            .subject(userDetails.username)
            .expiration(Date(now + accessExpiration))
            .signWith(accessSecretKey)
            .compact()
    }

    fun generateRefreshToken(
        refreshSecretKey: SecretKey,
        refreshExpiration: Long,
        userDetails: MemberDetails,
    ): String {
        val now = System.currentTimeMillis()

        return Jwts.builder()
            .claim("nickname", userDetails.getNickname())
            .subject(userDetails.username)
            .expiration(Date(now + refreshExpiration))
            .signWith(refreshSecretKey)
            .compact()
    }
}
