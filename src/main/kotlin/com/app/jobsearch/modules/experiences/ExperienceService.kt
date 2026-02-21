package com.app.jobsearch.modules.experiences

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.companies.CompanyRepository
import com.app.jobsearch.modules.experiences.requests.*
import com.app.jobsearch.modules.profile.ProfileRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExperienceService(
    private val repository: ExperienceRepository,
    private val mapper: ExperienceMapper,
    private val profileRepository: ProfileRepository
) {

    fun create(request: ExperienceCreateRequest): ExperienceResponse {

        if (request.role.isBlank()) {
            throw BusinessException(ExperienceError.ROLE_REQUIRED)
        }

        if (request.endDate != null && request.endDate.isBefore(request.startDate)) {
            throw BusinessException(ExperienceError.INVALID_DATE_RANGE)
        }

        val profile = profileRepository.findById(request.profileId)
            .orElseThrow { BusinessException(ExperienceError.PROFILE_NOT_FOUND) }

        val entity = mapper.toEntity(request, profile)

        return mapper.toResponse(repository.save(entity))
    }

    fun findById(id: UUID): ExperienceResponse {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ExperienceError.NOT_FOUND) }

        return mapper.toResponse(entity)
    }

    fun findAll(): List<ExperienceResponse> =
        repository.findAll().map { mapper.toResponse(it) }

    fun getByProfileId(profileId: UUID): List<ExperienceResponse> {
        profileRepository.findById(profileId)
            .orElseThrow { BusinessException(ExperienceError.PROFILE_NOT_FOUND) }

        return repository.findByProfileId(profileId)
            .map { mapper.toResponse(it) }
    }

    fun update(id: UUID, request: ExperienceUpdateRequest): ExperienceResponse {

        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ExperienceError.NOT_FOUND) }

        val start = request.startDate ?: entity.startDate
        val end = request.endDate ?: entity.endDate

        if (end != null && end.isBefore(start)) {
            throw BusinessException(ExperienceError.INVALID_DATE_RANGE)
        }

        mapper.updateEntity(entity, request)

        return mapper.toResponse(repository.save(entity))
    }

    fun delete(id: UUID) {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ExperienceError.NOT_FOUND) }

        repository.delete(entity)
    }
}