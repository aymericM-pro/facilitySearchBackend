package com.app.jobsearch.modules.experiences.requests

import java.time.LocalDate

data class ExperienceUpdateRequest(
    val role: String?,
    val location: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val summary: String?,
    val missions: List<String>?
)