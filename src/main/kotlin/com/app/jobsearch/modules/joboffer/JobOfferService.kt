package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.core.common.PageResponse
import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferResponse
import com.app.jobsearch.modules.joboffer.dto.UpdateJobOfferRequest
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class JobOfferService(
    private val repository: JobOfferRepository,
    private val mapper: JobOfferMapper
) {

    @PersistenceContext
    lateinit var entityManager: EntityManager


    fun create(request: CreateJobOfferRequest): JobOfferResponse {
        val entity = mapper.toEntity(request)
        validate(entity)
        val saved = repository.save(entity)
        return mapper.toResponse(saved)
    }

    fun findAll(
        criteria: JobOfferCriteria,
        pageable: Pageable
    ): PageResponse<JobOfferResponse> {

        val specification = JobOfferSpecification.withCriteria(criteria)

        val page = repository.findAll(specification, pageable)

        return PageResponse(
            content = page.content.map { mapper.toResponse(it) },
            page = page.number,
            size = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages
        )
    }

    fun findById(id: UUID): JobOfferResponse =
        repository.findById(id)
            .map { mapper.toResponse(it) }
            .orElseThrow { BusinessException(JobOfferError.NOT_FOUND) }

    fun update(id: UUID, dto: UpdateJobOfferRequest): JobOfferResponse {
        val existing = repository.findById(id).orElseThrow {
            BusinessException(JobOfferError.NOT_FOUND)
        }

        mapper.updateEntityFromDto(dto, existing)
        validate(existing)

        val saved = repository.save(existing)
        return mapper.toResponse(saved)
    }

    fun findAllByCompany(companyId: UUID, pageable: Pageable): PageResponse<JobOfferResponse> {
        val page = repository.findAllByCompanyId(companyId, pageable)
        return PageResponse(
            content = page.content.map { mapper.toResponse(it) },
            page = page.number,
            size = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages
        )
    }

    fun countByCompany(): List<CompanyJobOfferCount> = repository.countByCompany()

    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw BusinessException(JobOfferError.NOT_FOUND)
        }

        repository.deleteById(id)
    }

    private fun validate(jobOffer: JobOffer) {
        val min = jobOffer.salaryMin
        val max = jobOffer.salaryMax

        if (min != null && max != null && min > max) {
            throw BusinessException(JobOfferError.INVALID_SALARY_RANGE)
        }
    }

    @Transactional
    fun importBulk(requests: List<CreateJobOfferRequest>) {

        val batchSize = 50

        requests.forEachIndexed { index, request ->
            val entity = mapper.toEntity(request)
            validate(entity)

            entityManager.persist(entity)

            if (index % batchSize == 0) {
                entityManager.flush()
                entityManager.clear()
            }
        }
    }

}
