package com.unknowncat.orients.data.project.service

import com.unknowncat.orients.data.project.Project
import com.unknowncat.orients.data.project.ProjectData
import com.unknowncat.orients.data.project.ProjectRepository
import com.unknowncat.orients.data.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val projectData: ProjectData,
    private val userRepository: UserRepository
) {
    @Transactional
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
        resultList.removeIf { it.projectState == 3 }

        //process page
        val startIndex = (page - 1) * 10
        val endIndex = (page - 1) * 10 + 10

        for ((index, project) in resultList.toMutableList().withIndex())
            if (index < startIndex || index >= endIndex) resultList.remove(project)
        return resultList
    }

    @Transactional
    fun contract(project: Long, contractorId: Long) {
        val projectInstance = projectRepository.findById(project).get()
        val user = userRepository.findById(contractorId).get()
        projectInstance.projectState = 1
        projectInstance.activeUserId = contractorId
        projectInstance.latestUpdateTime = System.currentTimeMillis()
        user.activeProjectId.add(project.toString())
        projectRepository.save(projectInstance)
        userRepository.save(user)
    }
    @Transactional
    fun auditPass(projectId: Long) {
        val project = projectRepository.findById(projectId).get()
        project.projectState = 0
        projectRepository.save(project)
    }
    @Transactional
    fun auditDeclined(projectId: Long, reason: String) {
        val project = projectRepository.findById(projectId).get()
        project.projectState = 5
        project.notPassReason = reason
        projectRepository.save(project)
    }

    @Transactional
    fun uploadAttach(projectId: Long, pictures: List<String>, description: List<String>) {
        val project = projectRepository.findById(projectId).get()
        val pictureAttaches = arrayListOf<String>()
        pictureAttaches.addAll(pictures.map { "URL:$it" })
        pictureAttaches.addAll(description.map { "DESC:$it" })
        project.attaches.addAll(pictureAttaches)
        project.latestUpdateTime = System.currentTimeMillis()
        projectRepository.save(project)
    }

    @Transactional
    fun release(projectId: Long, releaseFileUrl: String) {
        val project = projectRepository.findById(projectId).get()
        project.projectState = 2
        project.releaseUrl = releaseFileUrl
        projectRepository.save(project)
    }
}