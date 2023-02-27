package com.randompicker.pobaba.config.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(
    private val jwtTokenProvider: JwtTokenProvider
) : ReactiveAuthenticationManager {

    @SuppressWarnings("unchecked")
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val token = authentication?.credentials.toString()

        return Mono.just(token).filter(jwtTokenProvider::validateToken).switchIfEmpty(Mono.empty()).flatMap {
            jwtTokenProvider.getAuthentication(token)
        }
    }

}