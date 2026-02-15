package com.app.jobsearch.joboffer

import org.springframework.data.jpa.domain.Specification
import jakarta.persistence.criteria.Predicate

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

            criteria.enterprises?.takeIf { it.isNotEmpty() }?.let { companies ->
                predicates += root.get<String>("company").`in`(companies)
            }

            criteria.skills?.takeIf { it.isNotEmpty() }?.let { skills ->

                skills.forEach { skill ->
                    val join = root.join<JobOffer, String>("tags")
                    predicates += cb.equal(join, skill)
                }

                query.distinct(true)
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}
