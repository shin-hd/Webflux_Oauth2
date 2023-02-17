package com.randompicker.pobaba.data.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.randompicker.pobaba.common.LoginType
import com.randompicker.pobaba.common.Role
import com.randompicker.pobaba.data.dto.oauth2.GithubResultDto
import com.randompicker.pobaba.data.dto.oauth2.GoogleResultDto
import com.randompicker.pobaba.data.dto.oauth2.KakaoResultDto
import com.randompicker.pobaba.data.dto.oauth2.NaverResultDto
import jakarta.validation.constraints.Email
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(value = "users")
class User(
    @Email val email: String?,
    val name: String,
    val picture: String?,
    val oauthId: String? = null,
    val type: LoginType = LoginType.NORMAL,
    val roles: List<String> = arrayListOf(),
    val locked: Boolean = false,
) : UserDetails, BaseEntity() {

    @MongoId
    lateinit var id: ObjectId
        private set

    companion object {
        fun fromGoogle(result: GoogleResultDto): User = User(
            result.emailAddresses.first { it.metadata.primary }.value,
            result.names.first { it.metadata.primary }.displayName,
            result.photos.firstOrNull { !it.default && it.metadata.primary }?.url,
            result.resourceName.split("/").last(),
            LoginType.GOOGLE,
            arrayListOf(Role.USER.role, Role.GUEST.role)
        )

        fun fromGithub(result: GithubResultDto): User = User(
            result.email,
            result.login,
            result.avatar_url,
            result.id,
            LoginType.GITHUB,
            arrayListOf(Role.USER.role, Role.GUEST.role)
        )

        fun fromNaver(response: NaverResultDto.NaverResponseDto): User = User(
            response.email,
            response.name,
            response.profile_image,
            response.id,
            LoginType.NAVER,
            arrayListOf(Role.USER.role, Role.GUEST.role)
        )

        fun fromKakao(result: KakaoResultDto): User = User(
            result.kakao_account.email,
            result.kakao_account.profile.nickname,
            result.kakao_account.profile.thumbnail_image_url,
            result.id,
            LoginType.KAKAO,
            arrayListOf(Role.USER.role, Role.GUEST.role)
        )
    }

    fun update(name: String, email: String?, picture: String): User {
        val updatedUser = User(
            email = email ?: this.email,
            name = name,
            picture = picture,
            oauthId = this.oauthId,
            type = this.type,
            locked = this.locked,
            roles = this.roles
        )
        updatedUser.id = this.id

        return updatedUser
    }

    override fun toString(): String =
        "id: $id, email: $email, name: $name, type: $type, locked: $locked, roles: $roles, createdDate: ${super.createdDate}, lastModifiedDate: ${super.lastModifiedDate}"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.roles.map { SimpleGrantedAuthority(it) }.toMutableList()
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword(): String {
        return password
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getUsername(): String {
        return id.toString();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonLocked(): Boolean {
        return !locked
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isEnabled(): Boolean {
        return !locked
    }

}