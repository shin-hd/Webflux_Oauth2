package com.randompicker.pobaba

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableReactiveMongoAuditing
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
