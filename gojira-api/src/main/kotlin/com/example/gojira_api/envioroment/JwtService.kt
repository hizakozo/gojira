package com.example.gojira_api.envioroment

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.Date
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtService(
    private val jwtConfig: JwtConfig
) {
    fun generateToken(userId: String, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtConfig.expiration * 1000)

        return Jwts.builder()
            .setSubject(userId)
            .claim("email", email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(jwtConfig.key)
            .compact()
    }
}