package com.randompicker.pobaba.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    //private val customOAuth2UserService: CustomOAuth2UserService,
) {

    @Bean
    fun filterChain(
        httpSecurity: ServerHttpSecurity,
        jwtTokenProvider: JwtTokenProvider,
    ): SecurityWebFilterChain {
        httpSecurity.httpBasic().disable()

            .csrf().disable()
            .formLogin().disable()

            .exceptionHandling()
            .authenticationEntryPoint { exchange, ex -> Mono.error(ex) }
            .accessDeniedHandler { exchange, ex -> Mono.error(ex) }

            .and()
            .authorizeExchange()
            .pathMatchers("/api/auth/**").permitAll()
            .pathMatchers("/api/**").authenticated()

            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                SecurityWebFiltersOrder.AUTHENTICATION
            )

        return httpSecurity.build()
    }

}