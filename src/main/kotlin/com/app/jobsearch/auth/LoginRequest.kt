package com.app.jobsearch.auth

data class LoginRequest(
    val email: String,
    val password: String
)