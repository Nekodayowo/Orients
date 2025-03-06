package com.unknowncat.orients.data.user.message

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
abstract class UserMessageRepository: JpaRepository<UserMessage, Long> {
    abstract fun findAllByReceiverId(receiverId: Long): MutableList<UserMessage>
    abstract fun removeAllByReceiverId(receiverId: Long)

}