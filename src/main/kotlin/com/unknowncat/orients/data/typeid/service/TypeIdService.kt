package com.unknowncat.orients.data.typeid.service

import com.unknowncat.orients.data.typeid.TypeId
import com.unknowncat.orients.data.typeid.TypeIdRepository
import org.springframework.stereotype.Service

@Service
class TypeIdService(private val typeIdRepository: TypeIdRepository) {
    fun getTopFloorTypeIds(): List<TypeId> {
        return typeIdRepository.getTopFloorTypeIds()
    }

    fun getAllSubTypeIds(parentTypeId: String): List<TypeId> {
        return typeIdRepository.getAllSubTypeIds(parentTypeId)
    }

    fun addSubTypeId(typePath: String, firstTypeName: String, secondTypeName: String, thirdTypeName: String, forthTypeName: String) {

        val pureTypePath = typePath.replace("#", "")

        val firstTypeId = pureTypePath.substring(0, 2)
        val secondTypeId = pureTypePath.substring(2, 4)
        val thirdTypeId = pureTypePath.substring(4, 6)
        val forthTypeId = pureTypePath.substring(6, 8)

        val addList = arrayListOf<TypeId>()
        if(!typeIdRepository.existsById(firstTypeName)){
            val firstTypeInstance = TypeId(firstTypeId, firstTypeName, "FF")
            addList.add(firstTypeInstance)
        }
        if(!typeIdRepository.existsById(secondTypeName)){
            val secondTypeInstance = TypeId(secondTypeId, secondTypeName, firstTypeId)
            addList.add(secondTypeInstance)
        }
        if(!typeIdRepository.existsById(thirdTypeName)){
            val thirdTypeInstance = TypeId(thirdTypeId, thirdTypeName, secondTypeId)
            addList.add(thirdTypeInstance)
        }
        if(!typeIdRepository.existsById(forthTypeName)){
            val forthTypeInstance = TypeId(forthTypeId, forthTypeName, thirdTypeId)
            addList.add(forthTypeInstance)
        }
        typeIdRepository.saveAll(addList)
    }

    fun isPathExists(typePath: String): Boolean {
        val pureTypePath = typePath.replace("#", "")

    }

    fun addTypeId(typeId: TypeId) {
        typeIdRepository.save(typeId)
    }
}