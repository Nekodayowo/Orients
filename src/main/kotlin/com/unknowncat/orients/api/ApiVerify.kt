package com.unknowncat.orients.api

import org.springframework.stereotype.Service

@Service
class ApiVerify {
    companion object{
        fun generateToken(userId: String): String{
            return DigestUtils.sha256Hex(userId)
        }
        fun verifyToken(userId: String, token: String): Boolean{
            return generateToken(userId) == token
        }
    }
}