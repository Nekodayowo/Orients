package com.unknowncat.orients.data.project

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
abstract class ProjectRepository : JpaRepository<Project, Long> {
    abstract fun findById(id: Int): Project?
    abstract fun findByProjectName(projectName: String): Project?
    abstract fun searchProjectByName(name: String): MutableList<Project>
}