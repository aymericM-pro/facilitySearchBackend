package com.app.jobsearch.joboffer

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "job_offers")
data class JobOffer(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var company: String,

    var location: String? = null,

    @Column(length = 5000)
    var description: String? = null,

    @ElementCollection
    @CollectionTable(
        name = "job_offer_tags",
        joinColumns = [JoinColumn(name = "job_offer_id")]
    )
    @Column(name = "tag")
    var tags: List<String> = emptyList(),

    var remote: Boolean? = null,

    var salaryMin: BigDecimal? = null,
    var salaryMax: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    var contractType: ContractType? = null,

    var createdAt: LocalDateTime? = null
)
