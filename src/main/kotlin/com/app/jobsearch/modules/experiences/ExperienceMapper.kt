package com.app.jobsearch.modules.experiences

import com.app.jobsearch.modules.companies.CompanyEntity
import com.app.jobsearch.modules.experiences.requests.*
import com.app.jobsearch.modules.profile.ProfileEntity
import org.springframework.stereotype.Component

@Component
class ExperienceMapper {

    fun toEntity(
        request: ExperienceCreateRequest,
        profile: ProfileEntity,
        company: CompanyEntity?
    ): ExperienceEntity =
        ExperienceEntity(
            profile = profile,
            company = company,
            role = request.role,
            location = request.location,
            startDate = request.startDate,
            endDate = request.endDate,
            description = request.description
        )

    fun toResponse(entity: ExperienceEntity): ExperienceResponse =
        ExperienceResponse(
            id = entity.id!!,
            profileId = entity.profile.id!!,
            companyId = entity.company?.id,
            role = entity.role,
            location = entity.location,
            startDate = entity.startDate,
            endDate = entity.endDate,
            description = entity.description
        )

    fun updateEntity(entity: ExperienceEntity, request: ExperienceUpdateRequest) {
        request.role?.let { entity.role = it }
        request.location?.let { entity.location = it }
        request.startDate?.let { entity.startDate = it }
        request.endDate?.let { entity.endDate = it }
        request.description?.let { entity.description = it }
    }
}