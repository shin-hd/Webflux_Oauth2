package com.randompicker.pobaba.service

import com.randompicker.pobaba.data.dto.UserResponseDto
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

interface UserDetailsService {

    fun loadUserByUsername(username:String): Mono<UserDetails>

    fun getUserInfoById(id: String): Mono<UserResponseDto>

}