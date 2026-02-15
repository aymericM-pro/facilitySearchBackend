package com.app.jobsearch.joboffer.dto

import com.app.jobsearch.joboffer.ContractType
import java.math.BigDecimal

data class CreateJobOfferRequest(
    val title: String,
    val company: String,
    val location: String? = null,
    val description: String? = null,
    val tags: List<String> = emptyList(),
    val remote: Boolean? = null,
    val salaryMin: BigDecimal? = null,
    val salaryMax: BigDecimal? = null,
    val contractType: ContractType? = null
)
