package com.unknowncat.orients.data.project.controller

import com.unknowncat.orients.api.ApiResponse
import com.unknowncat.orients.api.ApiVerify
import com.unknowncat.orients.data.project.Project
import com.unknowncat.orients.data.project.ProjectData
import com.unknowncat.orients.data.project.ProjectRepository
import com.unknowncat.orients.data.project.converter.ProjectConverter
import com.unknowncat.orients.data.project.service.ProjectService
import com.unknowncat.orients.tools.Repositories
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//import org.springframework.stereotype.Controller

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectData: ProjectData,
    private val projectRepository: ProjectRepository,
    private val projectConverter: ProjectConverter,
    private val projectService: ProjectService
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
        @RequestParam typeId: Long,
        @RequestParam publishLocation: String
        , @CookieValue userId: Long, @CookieValue token: String): ResponseEntity<ApiResponse> {
        if(!ApiVerify.verifyToken(token, userId.toString()))
            return invalidToken()

        if(projectName.length >= 30) return invalidParam("Project name too long")
        if(projectName.length <= 2) return invalidParam("Project name too short")

        if(projectDescription.length >= 300) return invalidParam("Project description too long")
        val projectInstance = Project(
            Repositories.generateUniqueId(projectRepository),
            projectName,
            projectDescription,
            projectOwnerId,
            userId,
            3,
            typeId.toString(),
            publishLocation = publishLocation,
        )

        projectService.createProject(projectInstance)

        return ok(projectConverter.convert(projectInstance))
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

}