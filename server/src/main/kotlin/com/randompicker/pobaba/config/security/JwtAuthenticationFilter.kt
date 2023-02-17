package com.randompicker.pobaba.config.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : WebFilter {

    private val logger: Logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = jwtTokenProvider.resolveToken(exchange.request)
        logger.info("token 추출. token : $token")
        MDC.put("uri", exchange.request.uri.toString())

        return when ((token != null) && jwtTokenProvider.validateToken(token)) {
            true -> {
                jwtTokenProvider.getAuthentication(token).flatMap {
                    SecurityContextHolder.getContext().authentication = it
                    chain.filter(exchange)
                }
            }
            else -> chain.filter(exchange)
        }
    }
}