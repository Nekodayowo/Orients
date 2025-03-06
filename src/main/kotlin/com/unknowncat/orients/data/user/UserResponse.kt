package com.unknowncat.orients.data.user

data class UserResponse(
    val id: Long,
    val username: String,
    val description: String,
    val phoneNumber: String,
    val email: String,
    val location: String,
    val permissionGroupId: Long,
    val finishedProjectId: List<String> = arrayListOf(),
    val activeProjectId: List<String> = arrayListOf(),
)