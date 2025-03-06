package com.unknowncat.orients.data.user.converter

import com.unknowncat.orients.api.Converter
import com.unknowncat.orients.data.user.User
import com.unknowncat.orients.data.user.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserConverter(private val userRepository: UserRepository) : Converter<User, UserResponse> {

    override fun convert(data: User): UserResponse {
        return UserResponse(data.id, data.username, data.description, data.phoneNumber, data.email, data.location, data.permissionGroupId, data.finishedProjectId, data.activeProjectId)
    }

    override fun inverse(obj: UserResponse): User? {
        return userRepository.findById(obj.id).getOrNull()
    }
}