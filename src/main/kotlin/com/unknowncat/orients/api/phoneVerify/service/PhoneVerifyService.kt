package com.unknowncat.orients.api.phoneVerify.service

import com.aliyun.dysmsapi20170525.models.SendSmsRequest
import com.aliyun.dysmsapi20170525.models.SendSmsResponse
import com.aliyun.teaopenapi.models.Config
import com.unknowncat.orients.api.ApiVerify
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class PhoneVerifyService {

    companion object{
        @Throws(Exception::class)
        fun createClient(): com.aliyun.dysmsapi20170525.Client {
            val config: Config = Config()
                .setAccessKeyId(("LTAI5tQvbci6qqGbdei1jxA3"))
                .setAccessKeySecret(("SUhzfOyyTLGr8NsKmmmJUTe1QYTWmg"))
            config.endpoint = "dysmsapi.aliyuncs.com"
            return com.aliyun.dysmsapi20170525.Client(config)
        }

        val smsClient = createClient()
    }

    val verifyCodes = mutableMapOf<String, String>() // phone -> verifyCode


    fun verify(phone: String, code: String): String {
        if(!verifyCodes.containsKey(phone)) return ""
        if(verifyCodes[phone] != code) return ""

        verifyCodes.remove(phone)
        return generatePhoneVerifyCode(phone)

    }

    fun generateTempCode(): String {
        return Random.nextInt(100000, 999999).toString()
    }

    fun sendVerificationCode(phone: String) {
        val tempCode = generateTempCode()
        verifyCodes[phone] = tempCode
        val sendSmsRequest = SendSmsRequest()
            .setPhoneNumbers(phone)
            .setSignName("钣金信息大王")
            .setTemplateCode("SMS_474165060")
            .setTemplateParam("{\"code\":\"$tempCode\"}")
        smsClient.sendSms(sendSmsRequest).body
        println()
    }

    fun generatePhoneVerifyCode(phone: String): String {
        return DigestUtils.sha256Hex(ApiVerify.generateToken(phone))
    }

    fun verifyToken(phone: String, token: String): Boolean {
        return generatePhoneVerifyCode(phone) == token
    }
}