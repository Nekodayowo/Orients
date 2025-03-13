package com.unknowncat.orients.api.phoneVerify.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/phone-verify")
class PhoneController(private val phoneVerifyService: PhoneVerifyService) {
    @GetMapping("/get")
    fun getEncryptedCode(@RequestParam phone : String, @RequestParam code: String): String {
        return phoneVerifyService.verify(phone, code)
    }

    @GetMapping("/send")
    fun sendSms(@RequestParam phone : String, @RequestParam code : String): String {
        phoneVerifyService.sendVerificationCode(phone)
        return "ok"
    }
}