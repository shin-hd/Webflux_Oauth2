package com.randompicker.pobaba.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Configuration(proxyBeanMethods = false)
class WebClientConfig {

    private val logger = LoggerFactory.getLogger(WebClientConfig::class.java)

    @Bean
    fun webClient(): WebClient {

        // 메모리 크기 증가
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer: ClientCodecConfigurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)
            }.build()

        // 로깅 설정
        exchangeStrategies
            .messageWriters()
            .filter(LoggingCodecSupport::class::isInstance)
            .forEach { (it as LoggingCodecSupport).isEnableLoggingRequestDetails = true }

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .filter(ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
                logger.debug("Request: ${clientRequest.method()} ${clientRequest.url()}")
                clientRequest.headers().forEach { (name, values) ->
                    values.forEach { logger.debug("$name : $it") }
                }
                Mono.just(clientRequest)
            })
            .filter(ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
                clientResponse.headers().asHttpHeaders().forEach { name, values ->
                    values.forEach {
                        logger.debug("$name : $it")
                    }
                }
                Mono.just(clientResponse)
            })
            .build()
    }

}