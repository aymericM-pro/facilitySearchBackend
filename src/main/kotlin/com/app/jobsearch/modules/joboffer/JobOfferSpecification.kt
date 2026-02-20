package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.companies.CompanyEntity
import com.app.jobsearch.modules.skills.SkillEntity
import org.springframework.data.jpa.domain.Specification
import jakarta.persistence.criteria.Predicate
import java.util.UUID

object JobOfferSpecification {

    fun withCriteria(criteria: JobOfferCriteria): Specification<JobOffer> {
        return Specification { root, query, cb ->

            val predicates = mutableListOf<Predicate>()

            criteria.remote?.let {
                predicates += cb.equal(root.get<Boolean>("remote"), it)
            }

            criteria.location?.let {
                predicates += cb.like(
                    cb.lower(root.get("location")),
                    "%${it.lowercase()}%"
                )
            }

            criteria.contractType?.let {
                predicates += cb.equal(root.get<ContractType>("contractType"), it)
            }

            criteria.enterprises
                ?.takeIf { it.isNotEmpty() }
                ?.let { companies ->
                    val companyJoin = root.join<JobOffer, CompanyEntity>("company")
                    predicates += companyJoin.get<String>("name").`in`(companies)
                }

            criteria.skills
                ?.takeIf { it.isNotEmpty() }
                ?.let { skills ->
                    val skillJoin = root.join<JobOffer, SkillEntity>("skills")
                    predicates += skillJoin.get<String>("name").`in`(skills)
                    query.distinct(true)
                }

            cb.and(*predicates.toTypedArray())
        }
    }
}
