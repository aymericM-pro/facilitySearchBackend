package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.companies.request.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Companies", description = "CRUD operations for companies")
interface CompanyApi {

    @Operation(
        summary = "Create company",
        responses = [
            ApiResponse(responseCode = "200", description = "Company created"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "409", description = "Already exists")
        ]
    )
    fun create(request: CreateCompanyRequest): ResponseEntity<CompanyResponse>

    @Operation(summary = "Get all companies")
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable
    ): ResponseEntity<Page<CompanyResponse>>

    @Operation(summary = "Get company by id")
    fun getById(id: UUID): ResponseEntity<CompanyResponse>

    @Operation(summary = "Update company")
    fun update(id: UUID, request: UpdateCompanyRequest): ResponseEntity<CompanyResponse>

    @Operation(summary = "Delete company")
    fun delete(id: UUID): ResponseEntity<Void>
}
