package com.randompicker.pobaba.data.dto.oauth2

data class GithubResultDto(
    val login: String,
    val id: String,
    val avatar_url: String?,
    val email: String?
) {
}