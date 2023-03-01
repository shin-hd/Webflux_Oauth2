package com.randompicker.pobaba.service.impl

import com.randompicker.pobaba.common.LoginType
import com.randompicker.pobaba.config.security.JwtTokenProvider
import com.randompicker.pobaba.data.dto.*
import com.randompicker.pobaba.data.dto.oauth2.GithubResultDto
import com.randompicker.pobaba.data.dto.oauth2.GoogleResultDto
import com.randompicker.pobaba.data.dto.oauth2.KakaoResultDto
import com.randompicker.pobaba.data.dto.oauth2.NaverResultDto
import com.randompicker.pobaba.data.entity.User
import com.randompicker.pobaba.data.repository.UserRepository
import com.randompicker.pobaba.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
    private val webClient: WebClient,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    @Value("\${spring.security.oauth2.client.provider.google.user-info-uri}") private val googleUserInfoUri: String,
    @Value("\${spring.security.oauth2.client.registration.github.client-id}") private val githubClientId: String,
    @Value("\${spring.security.oauth2.client.registration.github.client-secret}") private val githubClientSecret: String,
    @Value("\${spring.security.oauth2.client.provider.github.user-info-uri}") private val githubUserInfoUri: String,
    @Value("\${spring.security.oauth2.client.provider.naver.user-info-uri}") private val naverUserInfoUri: String,
    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}") private val kakaoUserInfoUri: String,
) : AuthService {

    private val logger: Logger = LoggerFactory.getLogger(AuthServiceImpl::class.java)

    override fun signInGoogle(signInDto: SignInDto): Mono<SignInResultDto> {
        val client: WebClient = webClient.mutate().baseUrl(googleUserInfoUri).build()

        return client.get().uri {
            it.queryParam("personFields", "emailAddresses,names,photos").build()
        }.accept(MediaType.APPLICATION_JSON)
            .headers {it.setBearerAuth(signInDto.access_token) }.retrieve()
            .onStatus({ it.is4xxClientError }) {
                logger.info("잘못된 액세스 토큰. access_token : ${signInDto.access_token}")
                Mono.error(IllegalArgumentException("Invalid access token received."))
            }
            .onStatus({ it.is5xxServerError }) {
                logger.info("리소스 서버 에러.")
                Mono.error(RuntimeException("Resource server error."))
            }
            .bodyToMono<GoogleResultDto>()

            .flatMap { googleResultDto ->
                val user = User.fromGoogle(googleResultDto)
                userRepository.findByOauthIdAndType(googleResultDto.resourceName.split("/").last(), LoginType.GOOGLE)
                    .flatMap {
                        logger.info("로그인 성공. user : $it")
                        Mono.just(
                            SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles))
                        )
                    }.switchIfEmpty(userRepository.save(user).flatMap {
                        logger.info("회원 등록 성공. user : $it")
                        Mono.just(SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles)))
                    })
            }
    }

    override fun signInGithub(signInDto: SignInDto): Mono<SignInResultDto> {
        val client: WebClient = webClient.mutate().build()

        return client.post().uri(githubUserInfoUri)
            .headers { it.add("Accept", "application/vnd.github+json") }
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fromFormData("client_id", githubClientId).with("client_secret", githubClientSecret)
                    .with("code", signInDto.access_token)
            ).retrieve()
            .onStatus({ it.is4xxClientError }) {
                logger.info("잘못된 코드. code : ${signInDto.access_token}")
                Mono.error(IllegalArgumentException("Invalid access token received."))
            }
            .onStatus({ it.is5xxServerError }) {
                logger.info("리소스 서버 에러.")
                Mono.error(RuntimeException("Resource server error."))
            }
            .bodyToMono<SignInDto>()

            .flatMap { signInDtoFromGithub ->
                client.get().uri("https://api.github.com/user")
                    .headers {
                    it.add("Accept", "application/vnd.github+json")
                    it.setBearerAuth(signInDtoFromGithub.access_token)
                }.retrieve()
                    .onStatus({ it.is4xxClientError }) {
                        logger.info("잘못된 액세스 토큰. access_token : ${signInDtoFromGithub.access_token}")
                        Mono.error(IllegalArgumentException("Invalid access token received."))
                    }
                    .onStatus({ it.is5xxServerError }) {
                        logger.info("리소스 서버 에러.")
                        Mono.error(RuntimeException("Resource server error."))
                    }
                    .bodyToMono<GithubResultDto>() }

            .flatMap { githubResultDto ->
                val user = User.fromGithub(githubResultDto)
                userRepository.findByOauthIdAndType(githubResultDto.id, LoginType.GITHUB).flatMap {
                    logger.info("로그인 성공. user : $it")
                    Mono.just(
                        SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles))
                    )
                }.switchIfEmpty(userRepository.save(user).flatMap {
                    logger.info("회원 등록 성공. user : $it")
                    Mono.just(SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles)))
                })
            }
    }

    override fun signInNaver(tokenDto: SignInDto): Mono<SignInResultDto> {
        val client: WebClient = webClient.mutate().baseUrl(naverUserInfoUri).build()

        return client.get().accept(MediaType.APPLICATION_JSON).headers {
            it.setBearerAuth(tokenDto.access_token)
        }.retrieve()
            .onStatus({ it.is4xxClientError }) {
                logger.info("잘못된 액세스 토큰. access_token : ${tokenDto.access_token}")
                Mono.error(IllegalArgumentException("Invalid access token received."))
            }
            .onStatus({ it.is5xxServerError }) {
                logger.info("리소스 서버 에러.")
                Mono.error(RuntimeException("Resource server error."))
            }
            .bodyToMono<NaverResultDto>()

            .flatMap { naverResultDto ->
                val user = User.fromNaver(naverResultDto.response)
                userRepository.findByOauthIdAndType(naverResultDto.response.id, LoginType.NAVER).flatMap {
                    logger.info("로그인 성공. user : $it")
                    Mono.just(
                        SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles))
                    )
                }.switchIfEmpty(userRepository.save(user).flatMap {
                    logger.info("회원 등록 성공. user : $it")
                    Mono.just(SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles)))
                })
            }
    }

    override fun signInKakao(tokenDto: SignInDto): Mono<SignInResultDto> {
        val client: WebClient = webClient.mutate().baseUrl(kakaoUserInfoUri).build()

        return client.get().accept(MediaType.APPLICATION_JSON).headers {
            it.setBearerAuth(tokenDto.access_token)
        }.retrieve()
            .onStatus({ it.is4xxClientError }) {
                logger.info("잘못된 액세스 토큰. access_token : ${tokenDto.access_token}")
                Mono.error(IllegalArgumentException("Invalid access token received."))
            }
            .onStatus({ it.is5xxServerError }) {
                logger.info("리소스 서버 에러.")
                Mono.error(RuntimeException("Resource server error."))
            }
            .bodyToMono<KakaoResultDto>()

            .flatMap { kakaoResultDto ->
                val user = User.fromKakao(kakaoResultDto)
                userRepository.findByOauthIdAndType(kakaoResultDto.id, LoginType.KAKAO).flatMap {
                    logger.info("로그인 성공. user : $it")
                    Mono.just(
                        SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles))
                    )
                }.switchIfEmpty(userRepository.save(user).flatMap {
                    logger.info("회원 등록 성공. user : $it")
                    Mono.just(SignInResultDto.from(jwtTokenProvider.createToken(it.id.toString(), it.roles)))
                })
            }
    }
}