package com.randompicker.pobaba.common.card

import com.randompicker.pobaba.data.entity.Card
import org.springframework.stereotype.Component

@Component
class CardGenerator(
) {

    private val bronzeCardList: List<Card> = listOf(
        Card(12, "엽록체", "광합성 중.", 500, 100, CardType.EARTH, 1),
        Card(13, "먼지", "떨어주세요!", 0, 100, CardType.AIR, 1),
        Card(14, "크릴", "대왕고래의 먹이", 100, 100, CardType.WATER, 1),
        Card(15, "털", "잘 날린다.", 100, 200, CardType.AIR, 1),
    )
    private val silverCardList: List<Card> = listOf(
        Card(8, "지렁이", "그냥 지렁이.", 500, 300, CardType.EARTH, 2),
        Card(9, "잠자리", "용파리.", 1500, 0, CardType.AIR, 2),
        Card(10, "물방개", "헤엄치는 물방개.", 100, 100, CardType.WATER, 2),
        Card(11, "곰벌레", "우주에서도 죽지 않는 곰벌레.", 0, 0, CardType.AETHER, 2),
    )
    private val goldCardList: List<Card> = listOf(
        Card(5, "온천 원숭이", "화산 온천수에 사는 원숭이. 크다.", 1500, 2300, CardType.Fire, 3),
        Card(6, "6", "숫자 6이다.", 1400, 1200, CardType.AIR, 3),
        Card(7, "딱정벌레", "단단한 딱정벌레.", 500, 500, CardType.WATER, 3),
    )
    private val platinumCardList: List<Card> = listOf(
        Card(2, "토끼", "족제비의 절친 토끼.", 100, 3000, CardType.EARTH, 4),
        Card(4, "오리", "이지스 레이더와 유도탄으로 무장한 오리.", 500, 3700, CardType.WATER, 4),
    )
    private val diamondCardList: List<Card> = listOf(
        Card(1, "족제비", "황금빛 털을 자랑하는 족제비.", 1000, 1000, CardType.EARTH, 5),
        Card(3, "노란 뿔", "노란 색의 뿔. 관통시킨다.", 4000, 100, CardType.AETHER, 5),
    )
    private val pickupCardList: List<Card> = listOf(
        Card(3, "노란 뿔", "노란 색의 뿔. 관통시킨다.", 4000, 100, CardType.AETHER, 5),
    )

    /*
    1.5%, 3%, 5%, 12%, 30%, 50%
     */
    private val probability: List<Int> = listOf(3, 6, 16, 40, 100, 200)

    fun getTenCards(): List<Card> = (1..10).map { getOneCard() }.toMutableList()

    fun getOneCard(): Card {
        val rank = (1..200).random()
        return when {
            rank <= probability[0] -> getOneCard(pickupCardList)
            rank <= probability[1] -> getOneCard(diamondCardList)
            rank <= probability[2] -> getOneCard(platinumCardList)
            rank <= probability[3] -> getOneCard(goldCardList)
            rank <= probability[4] -> getOneCard(silverCardList)
            rank <= probability[5] -> getOneCard(bronzeCardList)
            else -> throw RuntimeException()
        }
    }

    private fun getOneCard(cardList: List<Card>): Card = cardList[(cardList.indices).random()]

}