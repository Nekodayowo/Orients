package com.unknowncat.orients.data.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User, Long> {
    @Query(
        value = "select * from user where user.id = :userId and user.password = :password",
        nativeQuery = true
    )
    fun findUserByIdAndPassword(userId: Long, password: String): User?
    fun findUserByUsername(username: String): MutableList<User>
}