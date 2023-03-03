package com.randompicker.pobaba.service.impl

import com.randompicker.pobaba.common.card.CardGenerator
import com.randompicker.pobaba.data.dto.CardResponseDto
import com.randompicker.pobaba.data.repository.UserRepository
import com.randompicker.pobaba.service.CardService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class CardServiceImpl(
    private val cardGenerator: CardGenerator,
    private val userRepository: UserRepository
): CardService {

    override fun pickOnce(id: String): Mono<CardResponseDto> {
        return userRepository.findById(ObjectId(id)).flatMap {
            val card = cardGenerator.getOneCard()
            val updatedUser = it.update(it.cardList.plus(card))
            userRepository.save(updatedUser).map {
                CardResponseDto.from(card)
            }
        }
    }

    override fun pickTenTimes(id: String): Flux<CardResponseDto> {
        return userRepository.findById(ObjectId(id)).flatMap {
            val cardList = cardGenerator.getTenCards()
            val updatedUser = it.update(it.cardList.plus(cardList))
            userRepository.save(updatedUser).map {
                cardList.map(CardResponseDto::from).toMutableList()
            }
        }.flatMapIterable { it }
    }
}