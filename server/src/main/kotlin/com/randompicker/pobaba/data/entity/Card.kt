package com.randompicker.pobaba.data.entity

import com.randompicker.pobaba.common.card.CardType

class Card(
    val no: Int,
    val name: String,
    val description: String,
    val attack: Int,
    val defense: Int,
    val type: CardType,
    val rank: Byte,
) {
}