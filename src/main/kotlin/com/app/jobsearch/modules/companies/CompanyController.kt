package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.companies.request.*
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/companies")
class CompanyController(private val service: CompanyService) : CompanyApi {

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
}
