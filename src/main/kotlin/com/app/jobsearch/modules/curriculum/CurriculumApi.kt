package com.app.jobsearch.modules.curriculum

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

@Tag(
    name = "Curriculums",
    description = "Operations related to CV generation, download and listing"
)
interface CurriculumApi {

    @Operation(
        summary = "Trigger CV generation via n8n",
        responses = [
            ApiResponse(responseCode = "200", description = "CV successfully generated"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun trigger(
        @Parameter(description = "Trigger payload", required = true)
        request: TriggerN8nRequest,
        authentication: Authentication
    ): ResponseEntity<Map<String, String>>


    @Operation(
        summary = "Download a generated CV",
        responses = [
            ApiResponse(responseCode = "200", description = "Signed URL returned"),
            ApiResponse(responseCode = "403", description = "Access denied"),
            ApiResponse(responseCode = "404", description = "Curriculum not found")
        ]
    )
    fun download(
        @Parameter(description = "Curriculum ID", required = true)
        id: UUID,
        authentication: Authentication
    ): ResponseEntity<Map<String, String>>


    @Operation(
        summary = "Get all my generated CVs",
        responses = [
            ApiResponse(responseCode = "200", description = "List of user's CVs"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun myCurriculums(
        authentication: Authentication
    ): List<CurriculumEntity>


    @Operation(
        summary = "Get my CVs formatted for a specific offer",
        responses = [
            ApiResponse(responseCode = "200", description = "List of CVs with signed URLs"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun getMyByOffer(
        authentication: Authentication
    ): List<Map<String, String>>
}
