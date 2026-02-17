package com.app.jobsearch.modules.companies

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.companies.request.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CompanyService(
    private val repository: CompanyRepository,
    private val mapper: CompanyMapper
) {

    fun create(request: CreateCompanyRequest): CompanyResponse {

        if (request.name.isBlank()) {
            throw BusinessException(CompanyError.NAME_REQUIRED)
        }

        if (repository.existsByNameIgnoreCase(request.name.trim())) {
            throw BusinessException(CompanyError.ALREADY_EXISTS)
        }

        val entity = mapper.toEntity(request)
        val saved = repository.save(entity)

        return mapper.toResponse(saved)
    }

    fun findAll(pageable: Pageable): Page<CompanyResponse> =
        repository.findAll(pageable)
            .map { mapper.toResponse(it) }

    fun findById(id: UUID): CompanyResponse =
        repository.findById(id)
            .map { mapper.toResponse(it) }
            .orElseThrow { BusinessException(CompanyError.NOT_FOUND) }

    fun update(id: UUID, request: UpdateCompanyRequest): CompanyResponse {

        val existing = repository.findById(id)
            .orElseThrow { BusinessException(CompanyError.NOT_FOUND) }

        request.name?.let {
            if (it.isBlank()) throw BusinessException(CompanyError.NAME_REQUIRED)

            if (repository.existsByNameIgnoreCase(it.trim())
                && !it.equals(existing.name, true)
            ) {
                throw BusinessException(CompanyError.ALREADY_EXISTS)
            }
        }

        mapper.updateEntity(request, existing)

        val saved = repository.save(existing)
        return mapper.toResponse(saved)
    }

    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw BusinessException(CompanyError.NOT_FOUND)
        }
        repository.deleteById(id)
    }
}
