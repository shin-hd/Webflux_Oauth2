package com.randompicker.pobaba.config

import com.randompicker.pobaba.webflux.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.coRouter

@Configuration(proxyBeanMethods = false)
class RouterConfig: WebFluxConfigurer {

    @Bean
    fun mainRouter(userHandler: UserHandler) = coRouter {
        accept(TEXT_HTML).nest {
            (GET("/") or GET("/users/")).invoke(userHandler::hello)
            POST("/api/hello", userHandler::postStream)
        }
    }

    @Bean
    fun authRouter(userHandler: UserHandler) = coRouter {
        "/api/auth".nest {
            POST("/google", accept(APPLICATION_JSON), userHandler::signInGoogle)
            POST("/github", accept(APPLICATION_JSON), userHandler::signInGithub)
            POST("/naver", accept(APPLICATION_JSON), userHandler::signInNaver)
            POST("/kakao", accept(APPLICATION_JSON), userHandler::signInKakako)
        }
    }
}