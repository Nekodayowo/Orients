package com.unknowncat.orients.data.project.converter

import com.unknowncat.orients.api.Converter
import com.unknowncat.orients.data.project.Project
import com.unknowncat.orients.data.project.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectConverter(private val projectRepository: ProjectRepository) : Converter<Project, ProjectResponse> {
    override fun convert(obj: Project): ProjectResponse {
        return ProjectResponse(
            id = obj.id,
            name = obj.name,
            description = obj.description,
            createdTime = obj.createdTime,
            publishLocation = obj.publishLocation,
            createUserId = obj.createUserId,
            activeUserId = obj.activeUserId,
            projectState = obj.projectState,
            typeId = obj.typeId,
            latestUpdateTime = obj.latestUpdateTime
        )
    }
    override fun inverse(obj: ProjectResponse): Project? {
        return projectRepository.findById(obj.id).orElse(null)
    }
}