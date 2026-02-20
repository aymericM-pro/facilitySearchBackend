package com.app.jobsearch.modules.profile

import com.app.jobsearch.core.services.PdfService
import com.app.jobsearch.modules.education.EducationService
import com.app.jobsearch.modules.experiences.ExperienceService
import com.app.jobsearch.modules.profile.requests.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val service: ProfileService,
    private val profileService: ProfileService,
    private val experienceService: ExperienceService,
    private val educationService: EducationService,
    private val pdfService: PdfService
) : ProfileApi {

    @PostMapping
    override fun create(@RequestBody request: ProfileCreateRequest) =
        ResponseEntity.ok(service.create(request))

    @GetMapping
    override fun getAll() =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID) =
        ResponseEntity.ok(service.findById(id))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: ProfileUpdateRequest)
    = ResponseEntity.ok(service.update(id, request))

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/pdf")
    fun generatePdf(@PathVariable id: UUID): ResponseEntity<ByteArray> {

        val profile = profileService.findById(id)
        val experiences = experienceService.getByProfileId(id)
        val educations = educationService.getByProfileId(id)

        val pdfBytes = pdfService.generateCv(profile, experiences, educations)

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=cv.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }
}