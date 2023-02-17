package com.randompicker.pobaba.webflux

import com.randompicker.pobaba.data.dto.SignInWithCodeDto
import com.randompicker.pobaba.data.dto.SignInWithTokenDto
import com.randompicker.pobaba.service.AuthService
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Flux
import java.util.stream.Stream

inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

@Component
class UserHandler(
    @Autowired private val authService: AuthService
) {

    private val logger: Logger = LoggerFactory.getLogger(UserHandler::class.java);

    suspend fun hello(req: ServerRequest): ServerResponse {
        return ok().bodyAndAwait(Flux.just("Hello", "World!").asFlow())
    }

    suspend fun postStream(req: ServerRequest): ServerResponse {
        val body: Stream<Int> = Stream.iterate(0) { it + 1 }

        return ok().contentType(MediaType.TEXT_EVENT_STREAM).bodyValueAndAwait(
            fromPublisher(
                Flux.fromStream(body).map { mapOf(Pair("value", it)) }, typeReference<Map<String, Int>>()
            )
        )
    }

    suspend fun signInGoogle(req: ServerRequest): ServerResponse {
        val singInWithTokenDto = req.awaitBodyOrNull<SignInWithTokenDto>()

        val signInResultDto = singInWithTokenDto?.let {
            authService.signInGoogle(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInGithub(req: ServerRequest): ServerResponse {
        val signInWithCodeDto = req.awaitBodyOrNull<SignInWithCodeDto>()

        val signInResultDto = signInWithCodeDto?.let {
            authService.signInGithub(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInNaver(req: ServerRequest): ServerResponse {
        val signInWithTokenDto = req.awaitBodyOrNull<SignInWithTokenDto>()

        val signInResultDto = signInWithTokenDto?.let {
            authService.signInNaver(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

    suspend fun signInKakako(req: ServerRequest): ServerResponse {
        val signInWithTokenDto = req.awaitBodyOrNull<SignInWithTokenDto>()

        val signInResultDto = signInWithTokenDto?.let {
            authService.signInKakao(it).awaitSingle()
        }

        return if (signInResultDto == null) badRequest().buildAndAwait()
        else ok().bodyValueAndAwait(signInResultDto)
    }

}