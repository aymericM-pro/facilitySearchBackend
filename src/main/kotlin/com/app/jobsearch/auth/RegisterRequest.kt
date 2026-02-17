package com.app.jobsearch.auth

data class RegisterRequest(
    val email: String,
    val password: String,
    val pseudo: String
)