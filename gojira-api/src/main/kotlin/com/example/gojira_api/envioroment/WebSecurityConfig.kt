package com.example.gojira_api.envioroment

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(){

    val PUBLIC_API_PATH = "/**"

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .authorizeExchange { exchange ->
                exchange
                    .pathMatchers(PUBLIC_API_PATH).permitAll()
                    .anyExchange().authenticated()
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val c = CorsConfiguration()
        c.addAllowedMethod(CorsConfiguration.ALL)
        c.addAllowedHeader(CorsConfiguration.ALL)
        c.allowCredentials = true
        c.addAllowedOriginPattern("*") // Allow all origins with a pattern
        c.applyPermitDefaultValues()
        c.addExposedHeader("location")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", c)
        return source
    }
}
