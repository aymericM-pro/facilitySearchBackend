package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.UUID

interface JobOfferRepository :
    JpaRepository<JobOffer, UUID>,
    JpaSpecificationExecutor<JobOffer> {

    @EntityGraph(attributePaths = ["company"])
    override fun findAll(
        spec: Specification<JobOffer>,
        pageable: Pageable
    ): Page<JobOffer>

    @EntityGraph(attributePaths = ["company", "skills"])
    fun findAllByCompanyId(companyId: UUID, pageable: Pageable): Page<JobOffer>

    @Query("""
        SELECT new com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount(j.company.id, COUNT(j))
        FROM JobOffer j
        GROUP BY j.company.id
        ORDER BY COUNT(j) DESC
    """)
    fun countByCompany(): List<CompanyJobOfferCount>
}
