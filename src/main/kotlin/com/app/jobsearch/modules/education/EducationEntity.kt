package com.app.jobsearch.modules.education

import com.app.jobsearch.modules.profile.ProfileEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "educations")
data class EducationEntity(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity,

    @Column(nullable = false)
    var school: String,

    var degree: String? = null,

    var field: String? = null,

    var location: String? = null,

    var startDate: LocalDate,

    var endDate: LocalDate? = null,

    var logo: String? = null
)