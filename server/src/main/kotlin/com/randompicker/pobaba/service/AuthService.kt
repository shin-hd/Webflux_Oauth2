package com.randompicker.pobaba.service

import com.randompicker.pobaba.data.dto.SignInResultDto
import com.randompicker.pobaba.data.dto.SignInWithCodeDto
import com.randompicker.pobaba.data.dto.SignInWithTokenDto
import reactor.core.publisher.Mono

interface AuthService {

    fun signInGoogle(tokenDto: SignInWithTokenDto): Mono<SignInResultDto>

    fun signInGithub(codeDto: SignInWithCodeDto): Mono<SignInResultDto>

    fun signInNaver(tokenDto: SignInWithTokenDto): Mono<SignInResultDto>

    fun signInKakao(tokenDto: SignInWithTokenDto): Mono<SignInResultDto>

}