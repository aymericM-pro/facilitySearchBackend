package com.app.jobsearch.modules.applications

import com.app.jobsearch.modules.candidates.CandidateEntity
import com.app.jobsearch.modules.joboffer.JobOffer
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "applications")
data class ApplicationEntity (

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", nullable = false)
    var jobOffer: JobOffer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    var candidate: CandidateEntity,

    var status: String = "PENDING",

    var appliedAt: LocalDateTime? = LocalDateTime.now()
)
