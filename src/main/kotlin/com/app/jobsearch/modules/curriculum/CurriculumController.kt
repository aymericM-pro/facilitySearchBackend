package com.app.jobsearch.modules.curriculum

import com.app.jobsearch.auth.UserRepository
import com.app.jobsearch.joboffer.FileStorage
import com.app.jobsearch.openai.OpenAiService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import java.util.UUID

@RestController
@RequestMapping("/api/curriculum")
class CurriculumController(
    private val n8nService: N8nService,
    private val fileStorage: FileStorage,
    private val curriculumRepository: CurriculumRepository,
    private val userRepository: UserRepository,
    private val openAiService: OpenAiService
) {


    @PostMapping("/trigger")
    fun trigger(@Valid @RequestBody request: TriggerN8nRequest, authentication: Authentication):
            ResponseEntity<Map<String, String>> {
        val email = authentication.name
        val user = userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        val pseudo = user.pseudo

        val targetLine = try {
            openAiService.generateTargetLine(request.jobTitle, request.description)
        } catch (e: Exception) {
            "Je recherche un poste de ${request.jobTitle}."
        }

        val n8nResponse = n8nService.trigger(
            request = request,
            username = pseudo,
            targetLine = targetLine
        )

        val storagePath = n8nResponse.fileId
        val saved = curriculumRepository.save(
            CurriculumEntity(
                userName = pseudo,
                jobTitle = request.jobTitle,
                description = request.description,
                targetLine = targetLine,
                storagePath = storagePath
            )
        )

        val signedUrl = fileStorage.generateSignedUrl(storagePath)
        return ResponseEntity.ok(
            mapOf(
                "id" to saved.id.toString(),
                "fileName" to n8nResponse.fileName,
                "url" to signedUrl.toString()
            )
        )
    }

    @GetMapping("/download/{id}")
    fun download(
        @PathVariable id: UUID,
        authentication: Authentication
    ): ResponseEntity<Map<String, String>> {

        val email = authentication.name

        val user = userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        val curriculum = curriculumRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Curriculum not found") }

        if (curriculum.userName != user.pseudo) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied")
        }

        val url = fileStorage.generateSignedUrl(curriculum.storagePath)

        return ResponseEntity.ok(
            mapOf("url" to url.toString())
        )
    }

    @GetMapping("/my")
    fun myCurriculums(authentication: Authentication): List<CurriculumEntity> {

        val email = authentication.name

        val user = userRepository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        return curriculumRepository.findAllByUserName(user.pseudo)
    }

    @GetMapping("/by-offer")
    fun getMyByOffer(authentication: Authentication): List<Map<String, String>> {

        val email = authentication.name
        val user = userRepository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        val curriculums =
            curriculumRepository.findAllByUserName(user.pseudo)

        return curriculums.map {
            mapOf(
                "id" to it.id.toString(),
                "url" to fileStorage.generateSignedUrl(it.storagePath).toString(),
                "createdAt" to it.createdAt.toString(),
                "jobTitle" to it.jobTitle,
                "targetLine" to it.targetLine
            )
        }
    }
}
