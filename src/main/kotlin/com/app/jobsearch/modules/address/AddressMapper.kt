package com.app.jobsearch.modules.address

import com.app.jobsearch.modules.address.request.AddressResponse
import com.app.jobsearch.modules.address.request.CreateAddressRequest
import com.app.jobsearch.modules.address.request.UpdateAddressRequest
import org.springframework.stereotype.Component

@Component
class AddressMapper {

    fun toEntity(request: CreateAddressRequest): Address =
        Address(
            id = null,
            city = request.city.trim(),
            country = request.country?.label,
            postalCode = request.postalCode?.trim()
        )

    fun toResponse(entity: Address): AddressResponse =
        AddressResponse(
            id = entity.id!!,
            city = entity.city,
            country = entity.country,
            postalCode = entity.postalCode
        )

    fun updateEntity(dto: UpdateAddressRequest, entity: Address) {
        dto.city?.let { entity.city = it.trim() }
        dto.country?.let { entity.country = it.label }
        dto.postalCode?.let { entity.postalCode = it.trim() }
    }
}
