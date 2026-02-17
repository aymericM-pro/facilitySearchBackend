package com.app.jobsearch.modules.candidates

import com.app.jobsearch.modules.applications.ApplicationEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "candidates")
data class CandidateEntity (

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL])
    var applications: MutableList<ApplicationEntity> = mutableListOf()
)