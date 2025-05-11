package com.example.gojira_api.envioroment

import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {
    lateinit var secret: String
    var expiration: Long = 0
    val key: SecretKey get() =
        SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS256.jcaName)
}