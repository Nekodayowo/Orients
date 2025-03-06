package com.unknowncat.orients.data.typeid

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "typeid")
data class TypeId(
    @Id val id: String,
    @Column val name: String,
    @Column val parentTypeId: String
)