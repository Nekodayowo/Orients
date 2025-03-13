package com.unknowncat.orients.data.project

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findById(id: Int): Project?
    fun findByName(projectName: String): Project?
    fun searchProjectByName(name: String): MutableList<Project>
    fun findAllByProjectState(projectState: Int): MutableList<Project>
}