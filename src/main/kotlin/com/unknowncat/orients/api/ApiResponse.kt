package com.unknowncat.orients.api

class ApiResponse(val data: Any?, val message: String) {
    companion object{
        fun success(data: Any?): ApiResponse {
            return ApiResponse(data, "")
        }
        fun error(message: String): ApiResponse {
            return ApiResponse(null, message)
        }
        fun denied(reason: String): ApiResponse {
            return ApiResponse(null, "Denied by reason: $reason")
        }
    }
}