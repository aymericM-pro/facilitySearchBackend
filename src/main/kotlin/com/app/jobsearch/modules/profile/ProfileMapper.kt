package com.app.jobsearch.modules.profile

import com.app.jobsearch.modules.profile.requests.*
import org.springframework.stereotype.Component

@Component
class ProfileMapper {

    fun toEntity(request: ProfileCreateRequest): ProfileEntity =
        ProfileEntity(
            name = request.name,
            title = request.title,
            location = request.location,
            available = request.available,
            email = request.email,
            phone = request.phone,
            linkedin = request.linkedin,
            website = request.website,
            about = request.about,
            skills = request.skills.map { it.trim() }.toMutableList()
        )

    fun toResponse(entity: ProfileEntity): ProfileResponse =
        ProfileResponse(
            id = entity.id!!,
            name = entity.name,
            title = entity.title,
            location = entity.location,
            available = entity.available,
            email = entity.email,
            phone = entity.phone,
            linkedin = entity.linkedin,
            website = entity.website,
            about = entity.about,
            skills = entity.skills
        )

    fun updateEntity(entity: ProfileEntity, request: ProfileUpdateRequest) {
        request.name?.let { entity.name = it }
        request.title?.let { entity.title = it }
        request.location?.let { entity.location = it }
        request.available?.let { entity.available = it }
        request.email?.let { entity.email = it }
        request.phone?.let { entity.phone = it }
        request.linkedin?.let { entity.linkedin = it }
        request.website?.let { entity.website = it }
        request.about?.let { entity.about = it }
        request.skills?.let { entity.skills = it.toMutableList() }
    }
}