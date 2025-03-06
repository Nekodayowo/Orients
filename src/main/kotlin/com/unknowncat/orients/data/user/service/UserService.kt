package com.unknowncat.orients.data.user.service

import com.unknowncat.orients.data.user.User
import com.unknowncat.orients.data.user.UserRepository
import com.unknowncat.orients.data.user.message.UserMessage
import com.unknowncat.orients.data.user.message.UserMessageRepository
import com.unknowncat.orients.tools.Repositories
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMessageRepository: UserMessageRepository,
) {

}