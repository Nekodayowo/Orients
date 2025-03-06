package com.unknowncat.orients.data.user.controller

import com.unknowncat.orients.api.ApiResponse
import com.unknowncat.orients.api.ApiVerify
import com.unknowncat.orients.api.Controller
import com.unknowncat.orients.data.user.UserConverter
import com.unknowncat.orients.data.user.UserData
import com.unknowncat.orients.data.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/user")
class UserController(
    private val userConverter: UserConverter,
    private val userRepository: UserRepository,
    private val userData: UserData
) : Controller{

    @GetMapping("/info/{userId}")
    fun userInfo(@PathVariable userId: Long): ResponseEntity<ApiResponse> {
        return ok(userData.getUserById(userId))
    }
    @PostMapping("/sendMessage")
    fun sendMessage(@CookieValue token: String, @CookieValue userId: Long): ResponseEntity<ApiResponse> {
        if(ApiVerify.)

    }
}