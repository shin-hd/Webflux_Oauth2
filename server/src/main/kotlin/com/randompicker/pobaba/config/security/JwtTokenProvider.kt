package com.randompicker.pobaba.config.security

import com.randompicker.pobaba.service.UserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${spring.jwt.secret}")
    private val secret: String,
    private val userDetailsService: UserDetailsService,
) {

    private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    private lateinit var secretKey: SecretKey
    private val tokenValidMillisecond: Long = 1000L * 60 * 60 * 24 * 30

    @PostConstruct
    fun init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun createToken(userUid: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(userUid)
        claims["roles"] = roles
        val now = Date()

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(Date(now.time + tokenValidMillisecond))
            .signWith(secretKey).compact()
    }

    @Transactional
    fun getAuthentication(token: String): Mono<Authentication> {
        return userDetailsService.loadUserByUsername(getUsername(token)).map {
            UsernamePasswordAuthenticationToken(it, "", it.authorities)
        }
    }

    fun getUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun validateToken(token: String): Boolean {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        return !claims.body.expiration.before(Date())
    }

}