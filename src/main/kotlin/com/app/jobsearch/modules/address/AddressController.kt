package com.app.jobsearch.modules.address

import com.app.jobsearch.modules.address.request.AddressResponse
import com.app.jobsearch.modules.address.request.CreateAddressRequest
import com.app.jobsearch.modules.address.request.UpdateAddressRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/addresses")
class AddressController(private val service: AddressService) : AddressApi {

    @PostMapping
    override fun create(@RequestBody request: CreateAddressRequest): ResponseEntity<AddressResponse> =
        ResponseEntity.ok(service.create(request))

    @GetMapping
    override fun getAll(pageable: Pageable): ResponseEntity<Page<AddressResponse>> =
        ResponseEntity.ok(service.findAll(pageable))

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: UUID): ResponseEntity<AddressResponse> =
        ResponseEntity.ok(service.findById(id))

    @PutMapping("/{id}")
    override fun update(@PathVariable id: UUID, @RequestBody request: UpdateAddressRequest): ResponseEntity<AddressResponse> =
        ResponseEntity.ok(service.update(id, request))

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/cities")
    fun getCities(): List<String> = service.findAllCities()

    @GetMapping("/countries")
    fun getCountries(): List<Country> = Country.values().toList()
}
