package com.randompicker.pobaba.data.dto

import com.randompicker.pobaba.common.LoginType
import com.randompicker.pobaba.data.entity.User

data class UserResponseDto(
    val id: String,
    val email: String?,
    val name: String,
    val picture: String?,
    val oauthId: String? = null,
    val type: LoginType,
    val roles: List<String>,
    val locked: Boolean,
    ) {

    companion object {
        fun fromEntity(user: User): UserResponseDto = UserResponseDto(user.id.toString(), user.email, user.name, user.picture, user.oauthId, user.type, user.roles, user.locked)
    }

}