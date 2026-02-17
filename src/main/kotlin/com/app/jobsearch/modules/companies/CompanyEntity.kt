package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.joboffer.JobOffer
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "companies")
data class CompanyEntity (

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var name: String,

    var city: String? = null,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL])
    var jobOffers: MutableList<JobOffer> = mutableListOf()
)