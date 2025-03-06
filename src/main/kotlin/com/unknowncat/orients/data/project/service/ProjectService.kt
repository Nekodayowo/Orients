package com.unknowncat.orients.data.project.service

import com.unknowncat.orients.data.project.Project
import com.unknowncat.orients.data.project.ProjectData
import com.unknowncat.orients.data.project.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val projectData: ProjectData
) {
    fun createProject(projectInstance: Project) {
        projectRepository.save(projectInstance)
    }

    fun searchProject(
        projectName: String,
        startDuration: Long,
        endDuration: Long,
        createdBy: Long,
        projectTypeId: String,
        page: Int
    ): MutableList<Project> {
        val resultList = projectData.searchProject(projectName)

        resultList.removeIf { it.createdTime < startDuration || it.createdTime > endDuration }
        resultList.removeIf { it.createUserId != createdBy }
        resultList.removeIf { (projectTypeId !in it.typeId) }

        //process page
        val startIndex = (page - 1) * 10
        val endIndex = (page - 1) * 10 + 10

        for ((index, project) in resultList.toMutableList().withIndex())
            if (index < startIndex || index >= endIndex) resultList.remove(project)
        return resultList
    }
}