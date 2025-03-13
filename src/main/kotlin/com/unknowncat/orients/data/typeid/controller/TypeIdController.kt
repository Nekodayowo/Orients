package com.unknowncat.orients.data.typeid.controller

import com.unknowncat.orients.api.ApiResponse
import com.unknowncat.orients.api.Controller
import com.unknowncat.orients.data.typeid.TypeId
import com.unknowncat.orients.data.typeid.TypeIdRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/type")
class TypeIdController(private val typeIdRepository: TypeIdRepository) : Controller {

    @GetMapping("/tops")
    fun tops(): ResponseEntity<ApiResponse> {
        return ok(typeIdRepository.getTopFloorTypeIds())
    }

    @GetMapping("/subset")
    fun subset(parentId: String): ResponseEntity<ApiResponse> {
        return ok(typeIdRepository.getAllSubTypeIds(parentId))
    }
}