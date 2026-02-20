package com.app.jobsearch.modules.education.requests

import java.time.LocalDate
import java.util.UUID

data class EducationResponse(

    val id: UUID,
    val profileId: UUID,
    val school: String,
    val degree: String?,
    val field: String?,
    val location: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val logo: String?
)