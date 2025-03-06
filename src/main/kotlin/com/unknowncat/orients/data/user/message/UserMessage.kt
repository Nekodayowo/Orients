package com.unknowncat.orients.data.user.message

import jakarta.persistence.*


@Entity
@Table(name = "message")
data class UserMessage(
    @Id
    val messageId: Long,
    @Column
    val senderId: Long,
    @Column
    val receiverId: Long,
    @Column
    val sendTime: Long = System.currentTimeMillis(),
    @Column
    val messageContext: String,

)
