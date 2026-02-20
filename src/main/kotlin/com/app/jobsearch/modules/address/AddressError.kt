package com.app.jobsearch.modules.address

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class AddressError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND(code = "ADDRESS_NOT_FOUND", message = "Address not found", status = HttpStatus.NOT_FOUND),
    CITY_REQUIRED(code = "ADDRESS_CITY_REQUIRED", message = "City is required", status = HttpStatus.BAD_REQUEST)
}
