package com.unknowncat.orients.api

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service

@Service
class ApiVerify {
    companion object{

        const val ADMIN_TOKEN = "ABCDEF_ABC_123456::123"

        fun generateToken(userId: String): String{
            return DigestUtils.sha256Hex(userId)
        }
        fun verifyToken(userId: String, token: String): Boolean{
            return generateToken(userId) == token
        }
    }
}