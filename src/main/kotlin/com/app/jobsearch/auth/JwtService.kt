package com.app.jobsearch.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService {

    private val secret = Keys.hmacShaKeyFor(
        "my-super-secret-key-my-super-secret-key".toByteArray()
    )

    fun generateToken(user: UserEntity): String {
        return Jwts.builder()
            .setSubject(user.email)
            .claim("role", user.role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000))
            .signWith(secret, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}