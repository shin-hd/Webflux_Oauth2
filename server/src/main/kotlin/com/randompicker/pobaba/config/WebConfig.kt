package com.randompicker.pobaba.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
class WebConfig {

    @Bean
    fun resources(): WebProperties.Resources = WebProperties.Resources()

}