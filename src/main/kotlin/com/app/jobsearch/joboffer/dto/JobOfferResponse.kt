package com.app.jobsearch.joboffer.dto

import com.app.jobsearch.joboffer.ContractType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class JobOfferResponse(
    val id: UUID,
    val title: String,
    val company: String,
    val location: String?,
    val description: String?,
    val tags: List<String>,
    val remote: Boolean?,
    val salaryMin: BigDecimal?,
    val salaryMax: BigDecimal?,
    val contractType: ContractType?,
    val createdAt: LocalDateTime?
)