package com.app.jobsearch.modules.curriculum

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "curriculums")
class CurriculumEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val userName: String,

    @Column(nullable = false)
    val jobTitle: String,

    @Column(nullable = false)
    val targetLine: String,

    @Column(nullable = false)
    val storagePath: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now()
)
