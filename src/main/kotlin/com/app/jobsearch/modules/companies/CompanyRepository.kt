package com.app.jobsearch.modules.companies

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CompanyRepository : JpaRepository<CompanyEntity, UUID> {
    fun findByName(name: String): CompanyEntity?
    fun existsByNameIgnoreCase(name: String): Boolean
    fun findByNameIgnoreCase(name: String): CompanyEntity?
}
