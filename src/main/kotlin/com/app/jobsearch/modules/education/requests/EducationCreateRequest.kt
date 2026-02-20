package com.app.jobsearch.modules.education.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.util.UUID

data class EducationCreateRequest(
    @field:NotNull
    val profileId: UUID,
    @field:NotBlank
    val school: String,
    val degree: String? = null,
    val field: String? = null,
    val location: String? = null,
    @field:NotNull
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val logo: String? = null
)
