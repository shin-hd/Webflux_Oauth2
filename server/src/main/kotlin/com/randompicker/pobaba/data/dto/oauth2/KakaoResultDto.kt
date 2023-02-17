package com.randompicker.pobaba.data.dto.oauth2

data class KakaoResultDto(
    val id: String,
    val kakao_account: KakaoAccountDto,
) {
    data class KakaoAccountDto(
        val profile: Profile,
        val has_email: Boolean,
        val email: String,
    ) {
        data class Profile(
            val nickname: String,
            val thumbnail_image_url: String?,
            val profile_image_url: String?,
        ){}
    }
}