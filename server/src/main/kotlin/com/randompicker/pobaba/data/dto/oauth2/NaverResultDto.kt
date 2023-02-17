package com.randompicker.pobaba.data.dto.oauth2

data class NaverResultDto(
    val resultcode: Int,
    val message: String,
    val response: NaverResponseDto
) {
    data class NaverResponseDto(
        val id: String,
        val profile_image: String?,
        val email: String,
        val name: String,
    ) {}
}