package com.randompicker.pobaba.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun filterChain(
        httpSecurity: ServerHttpSecurity,
    ): SecurityWebFilterChain {
        return httpSecurity

            .exceptionHandling()
            .authenticationEntryPoint { exchange, ex ->
                Mono.fromRunnable {
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                }
            }
            .accessDeniedHandler { exchange, ex ->
                Mono.fromRunnable {
                    exchange.response.statusCode = HttpStatus.FORBIDDEN
                }
            }

            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()

            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)

            .authorizeExchange()
            .pathMatchers("/api/auth/**").permitAll()
            .anyExchange().authenticated()
            .and().build()
    }

}