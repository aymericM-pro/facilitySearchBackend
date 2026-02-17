package com.app.jobsearch.modules.companies

import com.app.jobsearch.modules.companies.request.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CompanyMapper {

    fun toEntity(request: CreateCompanyRequest): CompanyEntity =
        CompanyEntity(
            id = null,
            name = request.name.trim(),
            city = request.city,
            createdAt = LocalDateTime.now()
        )

    fun toResponse(entity: CompanyEntity): CompanyResponse =
        CompanyResponse(
            id = entity.id!!,
            name = entity.name,
            city = entity.city,
            createdAt = entity.createdAt
        )

    fun updateEntity(dto: UpdateCompanyRequest, entity: CompanyEntity) {
        dto.name?.let { entity.name = it.trim() }
        dto.city?.let { entity.city = it }
    }
}
