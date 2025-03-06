package com.unknowncat.orients.data.user.controller

import com.unknowncat.orients.api.ApiResponse
import com.unknowncat.orients.api.ApiVerify
import com.unknowncat.orients.api.Controller
import com.unknowncat.orients.data.user.User
import com.unknowncat.orients.data.user.converter.UserConverter
import com.unknowncat.orients.data.user.UserData
import com.unknowncat.orients.data.user.UserRepository
import com.unknowncat.orients.data.user.message.UserMessageService
import com.unknowncat.orients.tools.Repositories
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userConverter: UserConverter,
    private val userData: UserData,
    private val userMessageService: UserMessageService,
    private val userRepository: UserRepository,
) : Controller{

    @GetMapping("/info/{userId}")
    fun userInfo(@PathVariable userId: Long): ResponseEntity<ApiResponse> {
        val userInstance = userData.getUserById(userId) ?: return invalidParam("User not found")
        return ok(userConverter.convert(userInstance))
    }

    @GetMapping("/self")
    fun self(@CookieValue token: String, @CookieValue userId: Long): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidParam("Token invalid")
        val user = userData.getUserById(userId) ?: return invalidParam("User not found")
        return ok(userConverter.convert(user))
    }
    @PostMapping("/sendMessage")
    fun sendMessage(@RequestParam message: String, @RequestParam receiverId: Long, @CookieValue(defaultValue = "") token: String, @CookieValue(defaultValue = "") userId: Long): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidToken()
//        val messageInstance = UserMessage(Repositories.generateUniqueId(userMessageRepository), userId, receiverId, messageContext = message)
        userMessageService.sendMessage(userId = userId, receiverId = receiverId, message = message)
        return ok("ok")
    }

    @GetMapping("/messages")
    fun messages(@CookieValue token: String, @CookieValue userId: Long): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidToken()
        return ok(userMessageService.getAllMessages(userId))
    }

    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String, httpServletResponse: HttpServletResponse): ResponseEntity<ApiResponse> {
        val user = userData.getUserByUsernameAndPassword(username, password) ?: return invalidParam("Invalid username or password")

        val token = ApiVerify.generateToken(user.id.toString())
        val cookieToken = Cookie("token", token)
        val cookieUserId = Cookie("userId", user.id.toString())


        httpServletResponse.addCookie(cookieUserId)
        httpServletResponse.addCookie(cookieToken)

        return ok("login success")
    }

    @PostMapping("/register")
    fun register(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam phoneNumber: String,
        @RequestParam location: String,
        httpServletResponse: HttpServletResponse): ResponseEntity<ApiResponse> {
        val userUniqueId = Repositories.generateUniqueId(userRepository)
        val userInstance = User(
            id = userUniqueId,
            username = username,
            password = password,
            phoneNumber = phoneNumber,
            email = "",
            location = location,
        )
        userRepository.save(userInstance)
        return ok("Register success, please login to continue")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse> {
        return invalidParam("Exception occurred")
    }
}