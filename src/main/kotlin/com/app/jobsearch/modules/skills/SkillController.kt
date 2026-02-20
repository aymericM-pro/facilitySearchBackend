package com.app.jobsearch.modules.skills

import com.app.jobsearch.core.common.PageResponse
import com.app.jobsearch.modules.skills.request.*
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/skills")
class SkillController(private val service: SkillService) : SkillApi {

    @PostMapping
    override fun create(@RequestBody request: CreateSkillRequest): ResponseEntity<SkillResponse> =
        ResponseEntity.ok(service.create(request))

    @GetMapping
    override fun getAll(pageable: Pageable): ResponseEntity<PageResponse<SkillResponse>> =
        ResponseEntity.ok(service.findAll(pageable))

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID): ResponseEntity<SkillResponse> =
        ResponseEntity.ok(service.findById(id))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: UpdateSkillRequest): ResponseEntity<SkillResponse> =
        ResponseEntity.ok(service.update(id, request))

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
