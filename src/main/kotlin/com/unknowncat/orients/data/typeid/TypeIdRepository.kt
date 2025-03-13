package com.unknowncat.orients.data.typeid

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TypeIdRepository: JpaRepository<TypeId, String> {
    @Query("select * from typeid where parent_type_id = 'FF'", nativeQuery = true)
    fun getTopFloorTypeIds(): List<TypeId>

    @Query("select * from typeid where parent_type_id = :parentTypeId", nativeQuery = true)
    fun getAllSubTypeIds(parentTypeId: String): List<TypeId>
}