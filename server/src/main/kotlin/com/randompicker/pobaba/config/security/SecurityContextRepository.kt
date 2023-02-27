package com.randompicker.pobaba.config.security

import com.randompicker.pobaba.common.exception.InvalidTokenException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    private val authenticationManager: AuthenticationManager,
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw java.lang.UnsupportedOperationException("Not yet")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        val token = exchange?.request?.headers?.getFirst("X-AUTH-TOKEN").orEmpty()
            .let { UsernamePasswordAuthenticationToken(it, it) }

        return this.authenticationManager.authenticate(token).onErrorResume {
            Mono.error(InvalidTokenException("유효하지 않은 인증 토큰입니다."))
        }.map {
            SecurityContextImpl(it)
        }
    }

}