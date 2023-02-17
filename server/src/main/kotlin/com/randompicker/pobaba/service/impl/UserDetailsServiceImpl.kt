package com.randompicker.pobaba.service.impl

import com.randompicker.pobaba.data.repository.UserRepository
import com.randompicker.pobaba.service.UserDetailsService
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    private val logger:Logger = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)

    override fun loadUserByUsername(username: String): Mono<UserDetails> {
        return userRepository.findById(ObjectId(username)).cast(UserDetails::class.java)
    }

}