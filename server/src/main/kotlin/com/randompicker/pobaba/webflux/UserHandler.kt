package com.randompicker.pobaba.webflux

import com.randompicker.pobaba.service.UserDetailsService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class UserHandler(
    private val userDetailsService: UserDetailsService
) {

    private val logger: Logger = LoggerFactory.getLogger(UserHandler::class.java);

    suspend fun getUserInfo(req: ServerRequest): ServerResponse {
        val userId = req.principal().awaitSingle().name
        logger.info("사용자 정보 조회. userId: $userId")

        val userResponseDto = userDetailsService.getUserInfoById(userId).awaitSingle()

        return ok().bodyValueAndAwait(userResponseDto)
    }

}