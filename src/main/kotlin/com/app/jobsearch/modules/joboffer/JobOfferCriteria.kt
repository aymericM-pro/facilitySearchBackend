package com.app.jobsearch.modules.joboffer

data class JobOfferCriteria(
    val remote: Boolean? = null,
    val location: String? = null,
    val contractType: ContractType? = null,
    val enterprises: List<String>? = null,
    val skills: List<String>? = null
)
