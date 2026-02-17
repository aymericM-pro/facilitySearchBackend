package com.app.jobsearch.modules.skills

import com.app.jobsearch.modules.skills.request.CreateSkillRequest
import com.app.jobsearch.modules.skills.request.SkillResponse
import com.app.jobsearch.modules.skills.request.UpdateSkillRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Skills", description = "CRUD operations for skills")
interface SkillApi {

    @Operation(
        summary = "Create a skill",
        responses = [
            ApiResponse(responseCode = "200", description = "Skill created"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "409", description = "Skill already exists")
        ]
    )
    fun create(request: CreateSkillRequest): ResponseEntity<SkillResponse>


    @Operation(
        summary = "Get all skills (paginated)",
        responses = [
            ApiResponse(responseCode = "200", description = "Paginated skills")
        ]
    )
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable
    ): ResponseEntity<Page<SkillResponse>>


    @Operation(
        summary = "Get skill by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Skill found"),
            ApiResponse(responseCode = "404", description = "Skill not found")
        ]
    )
    fun getById(id: UUID): ResponseEntity<SkillResponse>


    @Operation(
        summary = "Update skill",
        responses = [
            ApiResponse(responseCode = "200", description = "Skill updated"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "404", description = "Skill not found"),
            ApiResponse(responseCode = "409", description = "Skill already exists")
        ]
    )
    fun update(id: UUID, request: UpdateSkillRequest): ResponseEntity<SkillResponse>


    @Operation(
        summary = "Delete skill",
        responses = [
            ApiResponse(responseCode = "204", description = "Deleted"),
            ApiResponse(responseCode = "404", description = "Skill not found")
        ]
    )
    fun delete(id: UUID): ResponseEntity<Void>
}
