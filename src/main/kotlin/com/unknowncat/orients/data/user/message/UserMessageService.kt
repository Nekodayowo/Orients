package com.unknowncat.orients.data.user.message

import com.unknowncat.orients.data.user.UserData
import com.unknowncat.orients.tools.Repositories
import org.springframework.stereotype.Service

@Service
class UserMessageService(
//    private val userRepository: UserRepository,
    private val userData: UserData,
    private val userMessageRepository: UserMessageRepository
) {
    fun sendMessage(userId: Long, receiverId: Long, message: String) {
        var generatedMessageId = Repositories.generateUniqueId(userMessageRepository)
        val messageInstance = UserMessage(messageId = generatedMessageId, userId, receiverId, messageContext = message)
        userMessageRepository.save(messageInstance)
    }

    fun getAllMessages(userId: Long): List<UserMessage> {
        val userMessages: List<UserMessage> = userMessageRepository.findAllByReceiverId(userId)
        userMessageRepository.removeAllByReceiverId(userId)
        return userMessages
    }
}