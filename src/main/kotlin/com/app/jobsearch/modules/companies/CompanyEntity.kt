package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.address.Address
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

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "address_id")
    var address: Address? = null,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var companyType: CompanyType? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var industry: Industry,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL])
    var jobOffers: MutableList<JobOffer> = mutableListOf()
)
