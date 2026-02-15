package com.app.jobsearch.modules.curriculum

import jakarta.validation.constraints.NotBlank


data class TriggerN8nRequest(
    @field:NotBlank
    val jobTitle: String,

    @field:NotBlank
    val targetLine: String
)