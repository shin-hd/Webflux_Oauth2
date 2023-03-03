package com.randompicker.pobaba.config

import com.randompicker.pobaba.webflux.AuthHandler
import com.randompicker.pobaba.webflux.CardHandler
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
    fun authRouter(authHandler: AuthHandler) = coRouter {
        "/api/auth".nest {
            POST("/login/google", accept(APPLICATION_JSON), authHandler::signInGoogle)
            POST("/login/github", accept(APPLICATION_JSON), authHandler::signInGithub)
            POST("/login/naver", accept(APPLICATION_JSON), authHandler::signInNaver)
            POST("/login/kakao", accept(APPLICATION_JSON), authHandler::signInKakako)
        }
    }

    @Bean
    fun userRouter(userHandler: UserHandler) = coRouter {
        "/api/users".nest {
            GET("/me", userHandler::getUserInfo)
        }
    }

    @Bean
    fun cardRouter(cardHandler: CardHandler) = coRouter {
        "/api/cards".nest {
            GET("/once", cardHandler::pickOnce)
            GET("/ten-times", cardHandler::pickTenTimes)
        }
    }

}