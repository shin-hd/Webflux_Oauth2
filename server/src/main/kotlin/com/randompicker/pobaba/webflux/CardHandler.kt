package com.randompicker.pobaba.webflux

import com.randompicker.pobaba.data.dto.CardResponseDto
import com.randompicker.pobaba.service.CardService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class CardHandler(
    private val cardService: CardService
) {

    private val logger: Logger = LoggerFactory.getLogger(CardHandler::class.java)

    suspend fun pickOnce(req: ServerRequest): ServerResponse {
        val userId = req.principal().awaitSingle().name

        val cardResponseDto = cardService.pickOnce(userId).awaitSingle()

        return ok().bodyValueAndAwait(cardResponseDto)
    }

    suspend fun pickTenTimes(req: ServerRequest): ServerResponse {
        val userId = req.principal().awaitSingle().name

        val cardResponseDtos = cardService.pickTenTimes(userId)

        return ok().body(cardResponseDtos, CardResponseDto::class.java).awaitSingle()
    }

}