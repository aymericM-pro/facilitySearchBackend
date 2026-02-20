package com.app.jobsearch.modules.experiences

import com.app.jobsearch.modules.education.requests.EducationResponse
import com.app.jobsearch.modules.experiences.requests.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/experiences")
class ExperienceController(
    private val service: ExperienceService
) : ExperienceApi {

    @PostMapping
    override fun create(@RequestBody request: ExperienceCreateRequest) =
        ResponseEntity.ok(service.create(request))

    @GetMapping
    override fun getAll() = ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID) = ResponseEntity.ok(service.findById(id))

    @GetMapping("/profile/{profileId}")
    override fun getByProfileId(@PathVariable profileId: UUID): ResponseEntity<List<ExperienceResponse>> =
        ResponseEntity.ok(service.getByProfileId(profileId))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: ExperienceUpdateRequest)
    = ResponseEntity.ok(service.update(id, request))

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}