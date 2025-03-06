package com.unknowncat.orients.data.user

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserData(
    private val userRepository: UserRepository,
)
{
    fun getUserById(userId: Long): User? {
        return userRepository.findById(userId).getOrNull()
    }

    fun getUserByUsernameAndPassword(userId: Long, password: String): User? {
        return userRepository.findUserByIdAndPassword(userId, password)
    }

    fun getUserByUsernameAndPassword(userId: String, password: String): User? {
        return userRepository.findUserByIdAndPassword(userId.toLong(), password)
    }
}