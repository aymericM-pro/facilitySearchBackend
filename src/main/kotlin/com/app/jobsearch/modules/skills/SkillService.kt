package com.app.jobsearch.modules.skills

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.skills.request.CreateSkillRequest
import com.app.jobsearch.modules.skills.request.SkillResponse
import com.app.jobsearch.modules.skills.request.UpdateSkillRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SkillService(
    private val repository: SkillRepository,
    private val mapper: SkillMapper
) {

    fun create(request: CreateSkillRequest): SkillResponse {

        if (request.name.isBlank()) {
            throw BusinessException(SkillError.NAME_REQUIRED)
        }

        val normalized = request.name.trim().lowercase()

        if (repository.existsByName(normalized)) {
            throw BusinessException(SkillError.ALREADY_EXISTS)
        }

        val entity = mapper.toEntity(request)
        val saved = repository.save(entity)

        return mapper.toResponse(saved)
    }

    fun findAll(pageable: Pageable): Page<SkillResponse> =
        repository.findAll(pageable)
            .map { mapper.toResponse(it) }

    fun findById(id: UUID): SkillResponse =
        repository.findById(id)
            .map { mapper.toResponse(it) }
            .orElseThrow { BusinessException(SkillError.NOT_FOUND) }

    fun update(id: UUID, request: UpdateSkillRequest): SkillResponse {

        val existing = repository.findById(id)
            .orElseThrow { BusinessException(SkillError.NOT_FOUND) }

        request.name?.let {
            if (it.isBlank()) {
                throw BusinessException(SkillError.NAME_REQUIRED)
            }

            val normalized = it.trim().lowercase()

            if (repository.existsByName(normalized) && normalized != existing.name) {
                throw BusinessException(SkillError.ALREADY_EXISTS)
            }
        }

        mapper.updateEntity(request, existing)

        val saved = repository.save(existing)
        return mapper.toResponse(saved)
    }

    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw BusinessException(SkillError.NOT_FOUND)
        }

        repository.deleteById(id)
    }
}
