package com.app.jobsearch.modules.experiences

import com.app.jobsearch.modules.experiences.requests.*
import com.app.jobsearch.modules.profile.ProfileEntity
import org.springframework.stereotype.Component

@Component
class ExperienceMapper {

    fun toEntity(request: ExperienceCreateRequest, profile: ProfileEntity) =
        ExperienceEntity(
            profile = profile,
            role = request.role,
            location = request.location,
            startDate = request.startDate,
            endDate = request.endDate,
            summary = request.summary,
            missions = request.missions.toMutableList()
        )

    fun toResponse(entity: ExperienceEntity) =
        ExperienceResponse(
            id = entity.id!!,
            profileId = entity.profile.id!!,
            role = entity.role,
            location = entity.location,
            startDate = entity.startDate,
            endDate = entity.endDate,
            summary = entity.summary,
            missions = entity.missions
        )

    fun updateEntity(entity: ExperienceEntity, request: ExperienceUpdateRequest): ExperienceEntity {
        request.role?.let { entity.role = it }
        request.location?.let { entity.location = it }
        request.startDate?.let { entity.startDate = it }
        request.endDate?.let { entity.endDate = it }
        request.summary?.let { entity.summary = it }
        request.missions?.let { entity.missions = it.toMutableList() }
        return entity
    }
}