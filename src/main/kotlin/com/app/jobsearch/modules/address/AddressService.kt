package com.app.jobsearch.modules.address

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.address.request.AddressResponse
import com.app.jobsearch.modules.address.request.CreateAddressRequest
import com.app.jobsearch.modules.address.request.UpdateAddressRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AddressService(
    private val repository: AddressRepository,
    private val mapper: AddressMapper
) {

    fun create(request: CreateAddressRequest): AddressResponse {
        if (request.city.isBlank()) {
            throw BusinessException(AddressError.CITY_REQUIRED)
        }

        val entity = mapper.toEntity(request)
        val saved = repository.save(entity)

        return mapper.toResponse(saved)
    }

    fun findAll(pageable: Pageable): Page<AddressResponse> =
        repository.findAll(pageable)
            .map { mapper.toResponse(it) }

    fun findById(id: UUID): AddressResponse =
        repository.findById(id)
            .map { mapper.toResponse(it) }
            .orElseThrow { BusinessException(AddressError.NOT_FOUND) }

    fun update(id: UUID, request: UpdateAddressRequest): AddressResponse {
        val existing = repository.findById(id)
            .orElseThrow { BusinessException(AddressError.NOT_FOUND) }

        request.city?.let {
            if (it.isBlank()) throw BusinessException(AddressError.CITY_REQUIRED)
        }

        mapper.updateEntity(request, existing)

        val saved = repository.save(existing)
        return mapper.toResponse(saved)
    }

    fun findAllCities(): List<String> = repository.findAllDistinctCities()

    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw BusinessException(AddressError.NOT_FOUND)
        }

        repository.deleteById(id)
    }
}
