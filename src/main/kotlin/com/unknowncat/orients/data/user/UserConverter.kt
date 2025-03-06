package com.unknowncat.orients.data.user

import com.unknowncat.orients.api.Converter
import org.springframework.stereotype.Service


@Service
class UserConverter: Converter<User, UserResponse> {

    override fun convert(data: User): UserResponse {
        return UserResponse(data.id, data.username, data.description, data.phoneNumber, data.email, data.location, data.permissionGroupId, data.finishedProjectId, data.activeProjectId)
    }
}