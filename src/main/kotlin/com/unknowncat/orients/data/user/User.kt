package com.unknowncat.orients.data.user

import com.unknowncat.orients.data.converter.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(
    @Id val id: Long,

    @Column val username: String,

    @Column val description: String = "",

    @Column val phoneNumber: String,

    @Column val email: String = "",

    @Column val location: String = "",

    @Column val permissionGroupId: Long = 0,

    @Convert(converter = StringListConverter::class)
    @Column val finishedProjectId: List<String> = arrayListOf(),

    @Convert(converter = StringListConverter::class)
    @Column val activeProjectId: List<String> = arrayListOf(),

    @Convert(converter = StringListConverter::class)
    @Column val createdProjectId: List<String> = arrayListOf(),

    @Column val password: String,

)