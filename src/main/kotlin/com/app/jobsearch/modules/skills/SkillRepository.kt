package com.app.jobsearch.modules.skills

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SkillRepository : JpaRepository<SkillEntity, UUID> {
    fun findByName(name: String): SkillEntity?
    fun existsByName(name: String): Boolean
}