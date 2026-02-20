package com.app.jobsearch.modules.education

import com.app.jobsearch.modules.education.requests.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/educations")
class EducationController(
    private val service: EducationService
) : EducationApi {

    @PostMapping
    override fun create(@RequestBody request: EducationCreateRequest): ResponseEntity<EducationResponse> =
        ResponseEntity.ok(service.createEducation(request))

    @GetMapping
    override fun getAll(): ResponseEntity<List<EducationResponse>> =
        ResponseEntity.ok(service.getAllEducation())


    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID): ResponseEntity<EducationResponse> =
        ResponseEntity.ok(service.getEducationById(id))

    @GetMapping("/profile/{profileId}")
    override fun getByProfileId(@PathVariable profileId: UUID): ResponseEntity<List<EducationResponse>> =
        ResponseEntity.ok(service.getByProfileId(profileId))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: EducationUpdateRequest): ResponseEntity<EducationResponse> =
        ResponseEntity.ok(service.updateEducation(id, request))


    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deleteEducation(id)
        return ResponseEntity.noContent().build()
    }
}
