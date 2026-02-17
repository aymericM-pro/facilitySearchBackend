package com.app.jobsearch.modules.candidates

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CandidateRepository : JpaRepository<CandidateEntity, UUID> {
    fun findByEmail(email: String): CandidateEntity?
}
