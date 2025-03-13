package com.unknowncat.orients.data.user.message

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserMessageRepository: JpaRepository<UserMessage, Long> {
    fun findAllByReceiverId(receiverId: Long): MutableList<UserMessage>
    fun removeAllByReceiverId(receiverId: Long)

}