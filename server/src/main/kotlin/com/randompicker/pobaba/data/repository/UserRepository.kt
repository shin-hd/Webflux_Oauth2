package com.randompicker.pobaba.data.repository

import com.randompicker.pobaba.common.LoginType
import com.randompicker.pobaba.data.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveMongoRepository<User, ObjectId> {

    fun findByOauthIdAndType(oauthId: String, type: LoginType): Mono<User>

}