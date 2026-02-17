package com.app.jobsearch.auth

import com.app.jobsearch.core.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateAccessToken(user: UserEntity): String =
        buildToken(user, jwtProperties.expiration)

    fun generateRefreshToken(user: UserEntity): String =
        buildToken(user, jwtProperties.refreshExpiration)

    private fun buildToken(user: UserEntity, ttl: Long): String {
        return Jwts.builder()
            .subject(user.email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + ttl))
            .signWith(getSigningKey())
            .compact()
    }

    fun extractEmail(token: String): String =
        extractClaim(token) { it.subject }

    fun isTokenExpired(token: String): Boolean =
        extractClaim(token) { it.expiration }.before(Date())

    private fun <T> extractClaim(token: String, resolver: (Claims) -> T): T {
        val claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload

        return resolver(claims)
    }
}
