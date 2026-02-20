package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.address.Address
import com.app.jobsearch.modules.companies.request.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CompanyMapper {

    fun toEntity(request: CreateCompanyRequest): CompanyEntity =
        CompanyEntity(
            id = null,
            name = request.name.trim(),
            address = request.city?.let {
                Address(city = it.trim())
            },
            createdAt = LocalDateTime.now(),
            companyType = request.companyType,
            industry = request.industry,
            jobOffers = mutableListOf()
        )

    fun toResponse(entity: CompanyEntity): CompanyResponse =
        CompanyResponse(
            id = entity.id!!,
            name = entity.name,
            city = entity.address?.city,
            createdAt = entity.createdAt,
            companyType = entity.companyType,
            industry = entity.industry
        )

    fun updateEntity(dto: UpdateCompanyRequest, entity: CompanyEntity) {
        dto.name?.let { entity.name = it.trim() }

        dto.city?.let {
            entity.address = Address(city = it.trim())
        }

        dto.companyType?.let { entity.companyType = it }
        dto.industry?.let { entity.industry = it }
    }
}

