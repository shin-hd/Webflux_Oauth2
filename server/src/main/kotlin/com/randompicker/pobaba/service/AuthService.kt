package com.randompicker.pobaba.service

import com.randompicker.pobaba.data.dto.SignInDto
import com.randompicker.pobaba.data.dto.SignInResultDto
import reactor.core.publisher.Mono

interface AuthService {

    fun signInGoogle(signInDto: SignInDto): Mono<SignInResultDto>

    fun signInGithub(signInDto: SignInDto): Mono<SignInResultDto>

    fun signInNaver(signInDto: SignInDto): Mono<SignInResultDto>

    fun signInKakao(signInDto: SignInDto): Mono<SignInResultDto>

}