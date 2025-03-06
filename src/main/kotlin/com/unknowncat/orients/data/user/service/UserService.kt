package com.unknowncat.orients.data.user.service

import com.unknowncat.orients.data.user.User
import com.unknowncat.orients.data.user.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository) {
}