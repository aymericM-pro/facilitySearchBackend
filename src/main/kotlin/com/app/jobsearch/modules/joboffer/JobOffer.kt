package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.applications.ApplicationEntity
import com.app.jobsearch.modules.companies.CompanyEntity
import com.app.jobsearch.modules.skills.SkillEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "job_offers")
class JobOffer(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: CompanyEntity,

    var location: String? = null,

    @Column(length = 5000)
    var description: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "job_offer_skills",
        joinColumns = [JoinColumn(name = "job_offer_id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id")]
    )
    var skills: MutableSet<SkillEntity> = mutableSetOf(),

    @OneToMany(
        mappedBy = "jobOffer",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var applications: MutableList<ApplicationEntity> = mutableListOf(),

    var remote: Boolean? = null,

    var salaryMin: BigDecimal? = null,
    var salaryMax: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    var contractType: ContractType? = null,

    var createdAt: LocalDateTime? = LocalDateTime.now()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is JobOffer) {
            return false
        }

        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    override fun toString(): String {
        return "JobOffer(id=$id, title='$title')"
    }
}
