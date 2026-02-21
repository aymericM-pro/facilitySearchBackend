package com.app.jobsearch.modules.experiences.requests

import java.time.LocalDate
import java.util.UUID

data class ExperienceCreateRequest(
    val profileId: UUID,
    val role: String,
    val location: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val summary: String?,
    val missions: List<String> = emptyList()
)