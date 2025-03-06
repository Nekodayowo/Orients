package com.unknowncat.orients.data.project

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ProjectData(
    private val projectRepository: ProjectRepository
) {
    fun getProjectInfo(id: Long): Project? {
        return projectRepository.findById(id).getOrNull()
    }

    fun getProjectInfoByProjectName(projectName: String): Project? {
        return projectRepository.findByProjectName(projectName)
    }

    fun searchProject(projectName: String): MutableList<Project> {
        return projectRepository.searchProjectByName(projectName)
    }
}