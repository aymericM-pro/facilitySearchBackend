package com.app.jobsearch.modules.joboffer.dto

import com.app.jobsearch.modules.joboffer.ContractType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class JobOfferResponse(
    val id: UUID,
    val title: String,
    val companyId: UUID,
    val companyName: String,
    val location: String?,
    val description: String?,
    val skills: List<String>,
    val remote: Boolean?,
    val salaryMin: BigDecimal?,
    val salaryMax: BigDecimal?,
    val contractType: ContractType?,
    val createdAt: LocalDateTime?
)
