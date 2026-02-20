package com.app.jobsearch.modules.profile

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.profile.requests.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProfileService(
    private val repository: ProfileRepository,
    private val mapper: ProfileMapper
) {

    fun create(request: ProfileCreateRequest): ProfileResponse {

        if (request.name.isBlank()) {
            throw BusinessException(ProfileError.NAME_REQUIRED)
        }

        val saved = repository.save(mapper.toEntity(request))
        return mapper.toResponse(saved)
    }

    fun findById(id: UUID): ProfileResponse {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ProfileError.NOT_FOUND) }

        return mapper.toResponse(entity)
    }

    fun findAll(): List<ProfileResponse> =
        repository.findAll().map { mapper.toResponse(it) }

    fun update(id: UUID, request: ProfileUpdateRequest): ProfileResponse {

        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ProfileError.NOT_FOUND) }

        request.name?.let {
            if (it.isBlank()) {
                throw BusinessException(ProfileError.NAME_REQUIRED)
            }
        }

        mapper.updateEntity(entity, request)

        return mapper.toResponse(repository.save(entity))
    }

    fun delete(id: UUID) {
        val entity = repository.findById(id)
            .orElseThrow { BusinessException(ProfileError.NOT_FOUND) }

        repository.delete(entity)
    }
}