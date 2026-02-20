package com.app.jobsearch.modules.companies.request

import com.app.jobsearch.modules.companies.CompanyType
import com.app.jobsearch.modules.companies.Industry

data class UpdateCompanyRequest(
    val name: String?,
    val city: String?,
    val companyType: CompanyType?,
    val industry: Industry?
)
