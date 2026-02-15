package com.app.jobsearch.joboffer

import com.app.jobsearch.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.joboffer.dto.JobOfferResponse
import com.app.jobsearch.joboffer.dto.UpdateJobOfferRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JobOfferMapper {

    fun toEntity(request: CreateJobOfferRequest): JobOffer {
        return JobOffer(
            id = null,
            title = request.title,
            company = request.company,
            location = request.location,
            description = request.description,
            tags = normalizeTags(request.tags),
            remote = request.remote ?: false,
            salaryMin = request.salaryMin,
            salaryMax = request.salaryMax,
            contractType = request.contractType,
            createdAt = LocalDateTime.now()
        )
    }

    fun toResponse(entity: JobOffer): JobOfferResponse {
        return JobOfferResponse(
            id = entity.id!!,
            title = entity.title,
            company = entity.company,
            location = entity.location,
            description = entity.description,
            tags = entity.tags,
            remote = entity.remote,
            salaryMin = entity.salaryMin,
            salaryMax = entity.salaryMax,
            contractType = entity.contractType,
            createdAt = entity.createdAt
        )
    }

    fun updateEntityFromDto(
        dto: UpdateJobOfferRequest,
        entity: JobOffer
    ) {
        dto.title?.let { entity.title = it }
        dto.company?.let { entity.company = it }
        dto.location?.let { entity.location = it }
        dto.description?.let { entity.description = it }
        dto.tags?.let { entity.tags = it }
        dto.remote?.let { entity.remote = it }
        dto.salaryMin?.let { entity.salaryMin = it }
        dto.salaryMax?.let { entity.salaryMax = it }
        dto.contractType?.let { entity.contractType = it }
    }

    private fun normalizeTags(tags: List<String>?): List<String> {
        if (tags == null) return emptyList()

        return tags
            .flatMap { it.split(";") }
            .map { it.trim().lowercase() }
            .filter { it.isNotBlank() }
            .distinct()
    }

}
