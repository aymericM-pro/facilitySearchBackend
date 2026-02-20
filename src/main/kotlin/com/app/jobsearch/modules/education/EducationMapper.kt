package com.app.jobsearch.modules.education

import com.app.jobsearch.modules.education.requests.EducationCreateRequest
import com.app.jobsearch.modules.education.requests.EducationResponse
import com.app.jobsearch.modules.education.requests.EducationUpdateRequest
import com.app.jobsearch.modules.profile.ProfileEntity
import org.springframework.stereotype.Component

@Component
class EducationMapper {

    fun toEntity(request: EducationCreateRequest, profile: ProfileEntity): EducationEntity =
        EducationEntity(
            profile = profile,
            school = request.school,
            degree = request.degree,
            field = request.field,
            location = request.location,
            startDate = request.startDate,
            endDate = request.endDate,
            logo = request.logo
        )

    fun toResponse(entity: EducationEntity): EducationResponse =
        EducationResponse(
            id = entity.id!!,
            profileId = entity.profile.id!!,
            school = entity.school,
            degree = entity.degree,
            field = entity.field,
            location = entity.location,
            startDate = entity.startDate,
            endDate = entity.endDate,
            logo = entity.logo
        )

    fun updateEntity(entity: EducationEntity, request: EducationUpdateRequest): EducationEntity {
        request.school?.let { entity.school = it }
        request.degree?.let { entity.degree = it }
        request.field?.let { entity.field = it }
        request.location?.let { entity.location = it }
        request.startDate?.let { entity.startDate = it }
        request.endDate?.let { entity.endDate = it }
        request.logo?.let { entity.logo = it }
        return entity
    }
}