package com.randompicker.pobaba.common.exception

import org.apache.http.entity.ContentType
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
@Order(-2)
class RestWebExceptionHandler: WebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        return when(ex) {
            is IllegalArgumentException -> {
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                val bytes = JSONObject(getErrorAttributes(ex)).toString().toByteArray()
                val buffer = bytes.let { exchange.response.bufferFactory().wrap(it) }
                exchange.response.writeWith(Flux.just(buffer))
            }
            else -> {
                exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                exchange.response.setComplete()
            }
        }
    }

    fun getErrorAttributes(ex: Throwable): MutableMap<String, Any> {
        return mutableMapOf(
            Pair("status", HttpStatus.BAD_REQUEST.value()),
            Pair("msg", ex.message as Any)
        )
    }

}