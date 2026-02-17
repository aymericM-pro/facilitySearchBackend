package com.app.jobsearch.modules.companies.request

data class CreateCompanyRequest(
    val name: String,
    val city: String?
)