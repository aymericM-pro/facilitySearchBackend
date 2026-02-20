package com.app.jobsearch.modules.experiences

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExperienceRepository: JpaRepository<ExperienceEntity, UUID> {
    fun findByProfileId(profileId: UUID): List<ExperienceEntity>
}