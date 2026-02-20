package com.app.jobsearch.modules.address

import com.app.jobsearch.modules.address.request.AddressResponse
import com.app.jobsearch.modules.address.request.CreateAddressRequest
import com.app.jobsearch.modules.address.request.UpdateAddressRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Addresses", description = "CRUD operations for addresses")
interface AddressApi {

    @Operation(
        summary = "Create an address",
        responses = [
            ApiResponse(responseCode = "200", description = "Address created"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun create(request: CreateAddressRequest): ResponseEntity<AddressResponse>

    @Operation(
        summary = "Get all addresses (paginated)",
        responses = [
            ApiResponse(responseCode = "200", description = "Paginated addresses")
        ]
    )
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable
    ): ResponseEntity<Page<AddressResponse>>

    @Operation(
        summary = "Get address by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Address found"),
            ApiResponse(responseCode = "404", description = "Address not found")
        ]
    )
    fun getById(id: UUID): ResponseEntity<AddressResponse>

    @Operation(
        summary = "Update address",
        responses = [
            ApiResponse(responseCode = "200", description = "Address updated"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "404", description = "Address not found")
        ]
    )
    fun update(id: UUID, request: UpdateAddressRequest): ResponseEntity<AddressResponse>

    @Operation(
        summary = "Delete address",
        responses = [
            ApiResponse(responseCode = "204", description = "Deleted"),
            ApiResponse(responseCode = "404", description = "Address not found")
        ]
    )
    fun delete(id: UUID): ResponseEntity<Void>
}
