package com.app.jobsearch.modules.profile.requests

import java.util.UUID

data class ProfileResponse(
    val id: UUID,
    val name: String,
    val title: String?,
    val location: String?,
    val available: Boolean,
    val email: String?,
    val phone: String?,
    val linkedin: String?,
    val website: String?,
    val about: String?,
    val skills: List<String>
)