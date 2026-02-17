package com.app.jobsearch.modules.joboffer.dto

import com.app.jobsearch.modules.joboffer.ContractType
import java.math.BigDecimal
import java.util.UUID

data class UpdateJobOfferRequest(
    val title: String? = null,
    val companyId: UUID? = null,
    val location: String? = null,
    val description: String? = null,
    val skills: List<String>? = null,
    val remote: Boolean? = null,
    val salaryMin: BigDecimal? = null,
    val salaryMax: BigDecimal? = null,
    val contractType: ContractType? = null
)
