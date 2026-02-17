package com.app.jobsearch.modules.applications

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApplicationRepository : JpaRepository<ApplicationEntity, UUID> {
    fun findByCandidateId(candidateId: UUID): List<ApplicationEntity>
    fun findByJobOfferId(jobOfferId: UUID): List<ApplicationEntity>
}
