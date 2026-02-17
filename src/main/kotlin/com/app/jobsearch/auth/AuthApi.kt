package com.app.jobsearch.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity


@Tag(name = "Authentication", description = "Authentication and JWT operations")
interface AuthApi {

    @Operation(
        summary = "Register a new user",
        security = [],
        responses = [
            ApiResponse(responseCode = "200", description = "User registered successfully"),
            ApiResponse(responseCode = "409", description = "Email already exists"),
            ApiResponse(responseCode = "400", description = "Invalid request")
        ]
    )
    fun register(request: AuthRequest): ResponseEntity<AuthResponse>


    @Operation(
        summary = "Login user",
        security = [],
        responses = [
            ApiResponse(responseCode = "200", description = "Authentication successful"),
            ApiResponse(responseCode = "401", description = "Invalid credentials"),
            ApiResponse(responseCode = "400", description = "Invalid request")
        ]
    )
    fun login(request: AuthRequest): ResponseEntity<AuthResponse>
}
