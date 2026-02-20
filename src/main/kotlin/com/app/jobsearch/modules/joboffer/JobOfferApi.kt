package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.core.common.PageResponse
import com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferResponse
import com.app.jobsearch.modules.joboffer.dto.UpdateJobOfferRequest
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = "Job Offers", description = "CRUD and filtering operations for job offers")
interface JobOfferApi {

    @Operation(
        summary = "Create a job offer",
        responses = [
            ApiResponse(responseCode = "200", description = "Job offer created"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun create(request: CreateJobOfferRequest): ResponseEntity<JobOfferResponse>


    @Operation(
        summary = "Get all job offers with filters",
        parameters = [
            Parameter(name = "remote", description = "Filter by remote"),
            Parameter(name = "location", description = "Filter by location (contains)"),
            Parameter(name = "contractType", description = "Filter by contract type"),
            Parameter(name = "enterprises", description = "Filter by enterprises"),
            Parameter(name = "skills", description = "Filter by skills")
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Paginated job offers")
        ]
    )
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable,
        remote: Boolean?,
        location: String?,
        contractType: ContractType?,
        enterprises: List<String>?,
        skills: List<String>?
    ): ResponseEntity<PageResponse<JobOfferResponse>>


    @Operation(
        summary = "Get job offer by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Job offer found"),
            ApiResponse(responseCode = "404", description = "Job offer not found")
        ]
    )
    fun getById(id: UUID): ResponseEntity<JobOfferResponse>


    @Operation(
        summary = "Update job offer",
        responses = [
            ApiResponse(responseCode = "200", description = "Job offer updated"),
            ApiResponse(responseCode = "404", description = "Job offer not found")
        ]
    )
    fun update(id: UUID, request: UpdateJobOfferRequest): ResponseEntity<JobOfferResponse>


    @Operation(
        summary = "Delete job offer",
        responses = [
            ApiResponse(responseCode = "204", description = "Deleted"),
            ApiResponse(responseCode = "404", description = "Not found")
        ]
    )
    fun delete(id: UUID): ResponseEntity<Void>


    @Operation(
        summary = "Count job offers grouped by company",
        responses = [
            ApiResponse(responseCode = "200", description = "Job offer counts per company, sorted by count desc")
        ]
    )
    fun countByCompany(): ResponseEntity<List<CompanyJobOfferCount>>


    @Operation(
        summary = "Import job offers from CSV",
        parameters = [
            Parameter(name = "file", description = "CSV file containing job offers", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Import successful"),
            ApiResponse(responseCode = "400", description = "Invalid file")
        ]
    )
    fun importCsv(file: MultipartFile): ResponseEntity<Map<String, Any>>
}
