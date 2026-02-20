package com.app.jobsearch.modules.education.requests

import java.time.LocalDate

data class EducationUpdateRequest(

    val school: String? = null,
    val degree: String? = null,
    val field: String? = null,
    val location: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val logo: String? = null
)