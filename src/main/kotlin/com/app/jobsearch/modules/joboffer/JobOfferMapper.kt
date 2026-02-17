package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.companies.CompanyRepository
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferResponse
import com.app.jobsearch.modules.joboffer.dto.UpdateJobOfferRequest
import com.app.jobsearch.modules.skills.SkillEntity
import com.app.jobsearch.modules.skills.SkillRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JobOfferMapper(
    private val companyRepository: CompanyRepository,
    private val skillRepository: SkillRepository
) {

    fun toEntity(request: CreateJobOfferRequest): JobOffer {

        val company = companyRepository.findById(request.companyId)
            .orElseThrow { IllegalArgumentException("Company not found") }

        val skills = resolveSkills(request.skills)

        return JobOffer(
            id = null,
            title = request.title,
            company = company,
            location = request.location,
            description = request.description,
            skills = skills,
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
            companyId = entity.company.id!!,
            companyName = entity.company.name,
            location = entity.location,
            description = entity.description,
            skills = entity.skills.map { it.name },
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

        dto.companyId?.let {
            val company = companyRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Company not found") }
            entity.company = company
        }

        dto.location?.let { entity.location = it }
        dto.description?.let { entity.description = it }

        dto.skills?.let {
            entity.skills.clear()
            entity.skills.addAll(resolveSkills(it))
        }

        dto.remote?.let { entity.remote = it }
        dto.salaryMin?.let { entity.salaryMin = it }
        dto.salaryMax?.let { entity.salaryMax = it }
        dto.contractType?.let { entity.contractType = it }
    }

    private fun resolveSkills(skillNames: List<String>?): MutableSet<SkillEntity> {

        if (skillNames.isNullOrEmpty()) return mutableSetOf()

        return skillNames
            .map { it.trim().lowercase() }
            .filter { it.isNotBlank() }
            .distinct()
            .map { name ->
                skillRepository.findByName(name)
                    ?: skillRepository.save(SkillEntity(name = name))
            }
            .toMutableSet()
    }
}
