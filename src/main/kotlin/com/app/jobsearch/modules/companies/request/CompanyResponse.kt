package com.app.jobsearch.modules.companies.request

import com.app.jobsearch.modules.companies.CompanyType
import com.app.jobsearch.modules.companies.Industry
import java.time.LocalDateTime
import java.util.UUID

data class CompanyResponse(
    val id: UUID,
    val name: String,
    val city: String?,
    val createdAt: LocalDateTime?,
    val companyType: CompanyType?,
    val industry: Industry
)
