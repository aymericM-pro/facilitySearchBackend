package com.app.jobsearch.modules.skills

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(name = "skills")
data class SkillEntity(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var name: String,
)
