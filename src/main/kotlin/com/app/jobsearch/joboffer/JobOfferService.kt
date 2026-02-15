package com.app.jobsearch.joboffer

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.joboffer.dto.JobOfferResponse
import com.app.jobsearch.joboffer.dto.UpdateJobOfferRequest
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
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

    fun findAll(pageable: Pageable, criteria: JobOfferCriteria): Page<JobOfferResponse> {
        val specification = JobOfferSpecification.withCriteria(criteria)
        return repository
            .findAll(specification, pageable)
            .map { mapper.toResponse(it) }
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
