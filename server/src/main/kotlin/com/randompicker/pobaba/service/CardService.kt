package com.randompicker.pobaba.service

import com.randompicker.pobaba.data.dto.CardResponseDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CardService {

    fun pickOnce(id: String): Mono<CardResponseDto>

    fun pickTenTimes(id: String): Flux<CardResponseDto>

}