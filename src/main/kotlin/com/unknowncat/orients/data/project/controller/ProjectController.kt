package com.unknowncat.orients.data.project.controller

import com.unknowncat.orients.api.ApiResponse
import com.unknowncat.orients.api.ApiVerify
import com.unknowncat.orients.data.project.Project
import com.unknowncat.orients.data.project.ProjectData
import com.unknowncat.orients.data.project.ProjectRepository
import com.unknowncat.orients.data.project.converter.ProjectConverter
import com.unknowncat.orients.data.project.service.ProjectService
import com.unknowncat.orients.data.typeid.service.TypeIdService
import com.unknowncat.orients.data.user.UserRepository
import com.unknowncat.orients.tools.Repositories
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

//import org.springframework.stereotype.Controller

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectData: ProjectData,
    private val projectRepository: ProjectRepository,
    private val projectConverter: ProjectConverter,
    private val projectService: ProjectService,
    private val userRepository: UserRepository,
    private val typeIdService: TypeIdService
) : com.unknowncat.orients.api.Controller {

    @GetMapping("/info/{id}")
    fun getProjectInfo(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        projectData.getProjectInfo(id)?.let { return ok(projectConverter.convert(it)) }
        return notFound("Project not found")
    }

    @PostMapping("/create")
    fun createProject(
        @RequestParam projectName: String,
        @RequestParam projectDescription: String,
        @RequestParam projectOwnerId: Long,
        @RequestParam projectId: Long,
        @RequestParam typeId: String,
        @RequestParam publishLocation: String
        ,@CookieValue(defaultValue = "") token: String, @CookieValue(defaultValue = "0") userId: Long): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidToken()

        if(projectName.length >= 30) return invalidParam("Project name too long")
        if(projectName.length <= 2) return invalidParam("Project name too short")

        if(!typeIdService.isTypeIdExist(typeId)) return invalidParam("Type id is not exists")

        if(projectDescription.length >= 300) return invalidParam("Project description too long")
        val projectInstance = Project(
            Repositories.generateUniqueId(projectRepository),
            projectName,
            projectDescription,
            projectOwnerId,
            userId,
            3,
            typeId,
            publishLocation = publishLocation,
        )

        projectService.createProject(projectInstance)

        return ok(projectConverter.convert(projectInstance))
    }

    @PostMapping("/contract")
    fun contract(
        @RequestParam projectId: Long,
        @CookieValue(defaultValue = "") token: String,
        @CookieValue(defaultValue = "0") userId: Long
    ): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidToken()
        val project = projectRepository.findById(projectId).getOrNull() ?: return notFound("Project not found")
        if(project.projectState != 0) return notFound("Project not found")

        projectService.contract(project.id, userId)

        return ok("Finished")
    }

    @GetMapping("/search")
    fun search(@RequestParam projectName: String,
               @RequestParam(defaultValue = "0") startDuration: Long,
               @RequestParam(defaultValue = Long.MAX_VALUE.toString()) endDuration: Long,
               @RequestParam(defaultValue = "-1") createdBy: Long,
               @RequestParam(defaultValue = "1") page: Int, // 10 items every page and it was a const value
               @RequestParam(defaultValue = "") projectTypeId: String,
               ): ResponseEntity<ApiResponse> {
        val resultList = projectService.searchProject(projectName, startDuration, endDuration, createdBy, projectTypeId, page)

        return ok(resultList.map { projectConverter.convert(it) })
    }

    @GetMapping("/unAuditProjects")
    fun unauditedProjects(@RequestParam adminToken: String): ResponseEntity<ApiResponse> {
        if(ApiVerify.ADMIN_TOKEN != adminToken) return notFound("Project not found")
        val projectList = projectRepository.findAllByProjectState(3)
        return ok(projectList)
    }


    @GetMapping("/pass")
    fun pass(@RequestParam projectId: Long): ResponseEntity<ApiResponse> {
        if(projectRepository.existsById(projectId))
            projectService.auditPass(projectId)
        else return notFound("Project not found")
        return ok("OK")
    }

}