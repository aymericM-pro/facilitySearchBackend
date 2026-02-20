package com.app.jobsearch.modules.experiences

import com.app.jobsearch.modules.experiences.requests.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Experiences", description = "CRUD operations for experiences")
interface ExperienceApi {

    @Operation(summary = "Create experience")
    fun create(request: ExperienceCreateRequest): ResponseEntity<ExperienceResponse>

    @Operation(summary = "Get all experiences")
    fun getAll(): ResponseEntity<List<ExperienceResponse>>

    @Operation(summary = "Get experiences by profile id")
    fun getByProfileId(profileId: UUID): ResponseEntity<List<ExperienceResponse>>

    @Operation(summary = "Get experience by id")
    fun getById(id: UUID): ResponseEntity<ExperienceResponse>

    @Operation(summary = "Update experience")
    fun update(id: UUID, request: ExperienceUpdateRequest): ResponseEntity<ExperienceResponse>

    @Operation(summary = "Delete experience")
    fun delete(id: UUID): ResponseEntity<Void>
}