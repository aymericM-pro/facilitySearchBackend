package com.app.jobsearch.modules.companies.request

import java.time.LocalDateTime
import java.util.UUID

data class CompanyResponse(
    val id: UUID,
    val name: String,
    val city: String?,
    val createdAt: LocalDateTime?
)