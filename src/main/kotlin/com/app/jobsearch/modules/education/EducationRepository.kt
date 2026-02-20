package com.app.jobsearch.modules.education

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EducationRepository: JpaRepository<EducationEntity, UUID> {
    fun findByProfileId(profileId: UUID): List<EducationEntity>
}
