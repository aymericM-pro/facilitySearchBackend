package com.app.jobsearch.modules.education

import com.app.jobsearch.modules.education.requests.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Educations", description = "CRUD operations for educations")
interface EducationApi {

    @Operation(
        summary = "Create education",
        responses = [
            ApiResponse(responseCode = "200", description = "Education created"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun create(request: EducationCreateRequest): ResponseEntity<EducationResponse>


    @Operation(
        summary = "Get all educations",
        responses = [
            ApiResponse(responseCode = "200", description = "List of educations")
        ]
    )
    fun getAll(): ResponseEntity<List<EducationResponse>>


    @Operation(
        summary = "Get education by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Education found"),
            ApiResponse(responseCode = "404", description = "Not found")
        ]
    )
    fun getById(id: UUID): ResponseEntity<EducationResponse>

    @Operation(
        summary = "Get educations by profile id",
        responses = [
            ApiResponse(responseCode = "200", description = "List of educations"),
            ApiResponse(responseCode = "404", description = "Not found")
        ]
    )
    fun getByProfileId(profileId: UUID): ResponseEntity<List<EducationResponse>>


    @Operation(
        summary = "Update education",
        responses = [
            ApiResponse(responseCode = "200", description = "Education updated"),
            ApiResponse(responseCode = "404", description = "Not found")
        ]
    )
    fun update(id: UUID, request: EducationUpdateRequest): ResponseEntity<EducationResponse>


    @Operation(
        summary = "Delete education",
        responses = [
            ApiResponse(responseCode = "204", description = "Deleted"),
            ApiResponse(responseCode = "404", description = "Not found")
        ]
    )
    fun delete(id: UUID): ResponseEntity<Void>
}