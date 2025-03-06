package com.unknowncat.orients.api.phoneVerify.service

import com.unknowncat.orients.api.ApiVerify
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils

@Service
class PhoneVerifyService {

    val verifyCodes = mutableMapOf<String, String>() // phone -> verifyCode


    fun verify(phone: String, code: String): String {
        if(!verifyCodes.containsKey(phone)) return ""
        if(verifyCodes[phone] != code) return ""

        verifyCodes.remove(phone)
        return generatePhoneVerifyCode(phone)

    }

    fun sendVerificationCode(phone: String) {
        // TODO: send verification code to the phone

        val encryptedToken = generatePhoneVerifyCode(phone)
        verifyCodes[phone] = encryptedToken
    }

    fun generatePhoneVerifyCode(phone: String): String {
        return DigestUtils.sha256Hex(ApiVerify.generateToken(phone))
    }

    fun verifyToken(phone: String, token: String): Boolean {
        return generatePhoneVerifyCode(phone) == token
    }
}