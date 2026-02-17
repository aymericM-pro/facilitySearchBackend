package com.app.jobsearch.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)