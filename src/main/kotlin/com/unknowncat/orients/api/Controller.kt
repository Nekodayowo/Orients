package com.unknowncat.orients.api

import com.unknowncat.orients.api.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface Controller {
    fun invalidToken(): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("Invalid token"))
    }

    fun notFound(message: String): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(message))
    }

    fun invalidParam(message: String): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(message))
    }

    fun ok(obj: Any?): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(obj))
    }
}
