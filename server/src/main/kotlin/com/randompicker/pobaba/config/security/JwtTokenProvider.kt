package com.randompicker.pobaba.config.security

import com.randompicker.pobaba.service.UserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    private var secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val tokenValidMillisecond: Long = 1000L * 60 * 60 * 24 * 30

    fun createToken(userUid: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(userUid)
        claims["roles"] = roles
        val now = Date()

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(Date(now.time + tokenValidMillisecond))
            //.signWith(SignatureAlgorithm.HS256, secretKey)
            .signWith(secretKey).compact()
    }

    @Transactional
    fun getAuthentication(token: String): Mono<Authentication> {
        return userDetailsService.loadUserByUsername(getUsername(token)).flatMap {
            logger.info("토큰 인증 정보 조회. userDetails : ${it.username}")
            Mono.just(
                UsernamePasswordAuthenticationToken(it, "", it.authorities)
            )
        }
    }

    fun getUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: ServerHttpRequest): String? {
        return request.headers.getFirst("X-AUTH-TOKEN")
    }

    fun validateToken(token: String): Boolean {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        return !claims.body.expiration.before(Date())
    }

}