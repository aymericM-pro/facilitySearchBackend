package com.app.jobsearch.modules.profile

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProfileRepository: JpaRepository<ProfileEntity, UUID> {
    fun findByName(name: String): List<ProfileEntity>
}