package com.app.jobsearch.modules.companies.request

import com.app.jobsearch.modules.companies.CompanyType
import com.app.jobsearch.modules.companies.Industry
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateCompanyRequest(

    @field:NotBlank
    val name: String,

    val city: String?,

    val companyType: CompanyType?,

    @field:NotNull
    val industry: Industry
)
