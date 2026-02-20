package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.companies.request.*
import com.app.jobsearch.modules.joboffer.JobOfferService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/companies")
class CompanyController(
    private val service: CompanyService,
    private val jobOfferService: JobOfferService
) : CompanyApi {

    @PostMapping
    override fun create(@RequestBody request: CreateCompanyRequest) = ResponseEntity.ok(service.create(request))

    @GetMapping
    override fun getAll(pageable: Pageable) = ResponseEntity.ok(service.findAll(pageable))

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID) = ResponseEntity.ok(service.findById(id))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: UpdateCompanyRequest)
    = ResponseEntity.ok(service.update(id, request))

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/job-offers")
    override fun getJobOffers(@PathVariable id: UUID, pageable: Pageable) =
        ResponseEntity.ok(jobOfferService.findAllByCompany(id, pageable))

    @GetMapping("/types")
    fun getCompanyTypes(): List<CompanyType> =
        CompanyType.values().toList()

    @GetMapping("/industries")
    fun getIndustries(): List<Industry> =
        Industry.values().toList()
}
