package com.app.jobsearch.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthRequest(

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val pseudo: String,

    @field:NotBlank
    val password: String
)
