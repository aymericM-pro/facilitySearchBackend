package com.app.jobsearch.modules.education

import com.app.jobsearch.modules.education.requests.EducationCreateRequest
import com.app.jobsearch.modules.education.requests.EducationResponse
import com.app.jobsearch.modules.education.requests.EducationUpdateRequest
import com.app.jobsearch.modules.profile.ProfileRepository
import org.springframework.stereotype.Service
import java.util.UUID
import com.app.jobsearch.core.error.BusinessException

@Service
class EducationService(
    private val educationRepository: EducationRepository,
    private val educationMapper: EducationMapper,
    private val profileRepository: ProfileRepository
) {

    fun getEducationById(educationId: UUID): EducationResponse {
        val entity = educationRepository.findById(educationId)
            .orElseThrow { BusinessException(EducationError.NOT_FOUND) }

        return educationMapper.toResponse(entity)
    }

    fun getAllEducation(): List<EducationResponse> {
        val educations = educationRepository.findAll()

        if (educations.isEmpty()) {
            throw BusinessException(EducationError.NOT_FOUND)
        }

        return educations.map { educationMapper.toResponse(it) }
    }

    fun getByProfileId(profileId: UUID): List<EducationResponse> {
        val educations = educationRepository.findByProfileId(profileId)

        if (educations.isEmpty()) {
            throw BusinessException(EducationError.NOT_FOUND)
        }

        return educations.map { educationMapper.toResponse(it) }
    }

    fun createEducation(request: EducationCreateRequest): EducationResponse {

        if (request.school.isBlank()) {
            throw BusinessException(EducationError.SCHOOL_REQUIRED)
        }

        if (request.endDate != null && request.endDate.isBefore(request.startDate)) {
            throw BusinessException(EducationError.INVALID_DATE_RANGE)
        }

        val profile = profileRepository.findById(request.profileId)
            .orElseThrow { BusinessException(EducationError.PROFILE_NOT_FOUND) }

        val entity = educationMapper.toEntity(request, profile)
        val saved = educationRepository.save(entity)

        return educationMapper.toResponse(saved)
    }

    fun updateEducation(
        educationId: UUID,
        request: EducationUpdateRequest
    ): EducationResponse {

        val entity = educationRepository.findById(educationId)
            .orElseThrow { BusinessException(EducationError.NOT_FOUND) }

        request.school?.let {
            if (it.isBlank()) {
                throw BusinessException(EducationError.SCHOOL_REQUIRED)
            }
        }

        val start = request.startDate ?: entity.startDate
        val end = request.endDate ?: entity.endDate

        if (end != null && end.isBefore(start)) {
            throw BusinessException(EducationError.INVALID_DATE_RANGE)
        }

        educationMapper.updateEntity(entity, request)
        val updated = educationRepository.save(entity)

        return educationMapper.toResponse(updated)
    }

    fun deleteEducation(educationId: UUID) {
        val entity = educationRepository.findById(educationId)
            .orElseThrow { BusinessException(EducationError.NOT_FOUND) }

        educationRepository.delete(entity)
    }
}