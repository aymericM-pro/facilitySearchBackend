package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.core.common.PageResponse
import com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferResponse
import com.app.jobsearch.modules.joboffer.dto.UpdateJobOfferRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/api/job-offers")
class JobOfferController(
    private val service: JobOfferService,
    private val csvImporter: JobOfferCsvImporter
) :
    JobOfferApi {

    @PostMapping
    override fun create(@RequestBody request: CreateJobOfferRequest): ResponseEntity<JobOfferResponse> =
        ResponseEntity.ok(service.create(request))
    @GetMapping
    override fun getAll(
        pageable: Pageable,
        @RequestParam(required = false) remote: Boolean?,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) contractType: ContractType?,
        @RequestParam(required = false) enterprises: List<String>?,
        @RequestParam(required = false) skills: List<String>?
    ): ResponseEntity<PageResponse<JobOfferResponse>> {

        val criteria = JobOfferCriteria(
            remote = remote,
            location = location,
            contractType = contractType,
            enterprises = enterprises,
            skills = skills
        )

        val result = service.findAll(criteria, pageable)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID): ResponseEntity<JobOfferResponse> =
        ResponseEntity.ok(service.findById(id))


    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: UpdateJobOfferRequest): ResponseEntity<JobOfferResponse> =
        ResponseEntity.ok(service.update(id, request))


    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }


    @GetMapping("/stats/by-company")
    override fun countByCompany(): ResponseEntity<List<CompanyJobOfferCount>> =
        ResponseEntity.ok(service.countByCompany())

    @PostMapping("/import")
    override fun importCsv(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, Any>> {
        val requests = csvImporter.parse(file.inputStream)
        service.importBulk(requests)

        return ResponseEntity.ok(
            mapOf(
                "imported" to requests.size,
                "status" to "SUCCESS"
            )
        )
    }
}
