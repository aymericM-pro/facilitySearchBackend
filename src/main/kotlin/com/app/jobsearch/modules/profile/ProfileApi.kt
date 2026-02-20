package com.app.jobsearch.modules.profile

import com.app.jobsearch.modules.profile.requests.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Profiles", description = "CRUD operations for profiles")
interface ProfileApi {

    @Operation(summary = "Create profile")
    fun create(request: ProfileCreateRequest): ResponseEntity<ProfileResponse>

    @Operation(summary = "Get all profiles")
    fun getAll(): ResponseEntity<List<ProfileResponse>>

    @Operation(summary = "Get profile by id")
    fun getById(id: UUID): ResponseEntity<ProfileResponse>

    @Operation(summary = "Update profile")
    fun update(id: UUID, request: ProfileUpdateRequest): ResponseEntity<ProfileResponse>

    @Operation(summary = "Delete profile")
    fun delete(id: UUID): ResponseEntity<Void>
}