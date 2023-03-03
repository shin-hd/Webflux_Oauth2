package com.randompicker.pobaba.data.dto

import com.randompicker.pobaba.common.CommonResponse
import com.randompicker.pobaba.common.card.CardType
import com.randompicker.pobaba.data.entity.Card

data class CardResponseDto(
    val no: Int,
    val name: String,
    val description: String,
    val attack: Int,
    val defense: Int,
    val type: CardType,
    val rank: Byte,
) {
    companion object {
        fun from(card: Card): CardResponseDto = CardResponseDto(
            no = card.no,
            name = card.name,
            description = card.description,
            attack = card.attack,
            defense = card.defense,
            type = card.type,
            rank = card.rank
        )
    }
}