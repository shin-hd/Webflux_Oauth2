package com.randompicker.pobaba.webflux

import com.randompicker.pobaba.data.dto.SignInDto
import com.randompicker.pobaba.service.AuthService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class AuthHandler(
    @Autowired private val authService: AuthService
) {

    private val logger: Logger = LoggerFactory.getLogger(AuthHandler::class.java);

    suspend fun signInGoogle(req: ServerRequest): ServerResponse {
        val signInDto = req.awaitBodyOrNull<SignInDto>()
        logger.info("구글 로그인 요청.")
        
        val signInResultDto = signInDto?.let {
            authService.signInGoogle(it).awaitSingle()
        }
        
        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInGithub(req: ServerRequest): ServerResponse {
        val signInDto = req.awaitBodyOrNull<SignInDto>()
        logger.info("깃허브 로그인 요청.")

        val signInResultDto = signInDto?.let {
            authService.signInGithub(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInNaver(req: ServerRequest): ServerResponse {
        val signInDto = req.awaitBodyOrNull<SignInDto>()
        logger.info("네이버 로그인 요청.")

        val signInResultDto = signInDto?.let {
            authService.signInNaver(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInKakako(req: ServerRequest): ServerResponse {
        val signInDto = req.awaitBodyOrNull<SignInDto>()
        logger.info("카카오 로그인 요청.")

        val signInResultDto = signInDto?.let {
            authService.signInKakao(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

}