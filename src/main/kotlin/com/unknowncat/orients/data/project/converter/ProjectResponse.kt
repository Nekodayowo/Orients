package com.unknowncat.orients.data.project.converter

import com.unknowncat.orients.data.project.Project

data class ProjectResponse (
    var id: Long,
    var name: String,
    var description: String,
    var createUserId: Long,
    var activeUserId: Long,
    var projectState: Int,
    var typeId: String,
    var createdTime: Long,
    var latestUpdateTime: Long,
    var publishLocation: String,
)