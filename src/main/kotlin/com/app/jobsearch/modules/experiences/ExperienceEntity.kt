package com.app.jobsearch.modules.experiences

import com.app.jobsearch.modules.companies.CompanyEntity
import com.app.jobsearch.modules.profile.ProfileEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "experiences")
data class ExperienceEntity(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity,

    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: CompanyEntity? = null,

    @Column(nullable = false)
    var role: String,

    var location: String? = null,

    var startDate: LocalDate,

    var endDate: LocalDate? = null,

    var description: String? = null
)
