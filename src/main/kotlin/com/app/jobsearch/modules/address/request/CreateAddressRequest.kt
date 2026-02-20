package com.app.jobsearch.modules.address.request

import com.app.jobsearch.modules.address.Country
import jakarta.validation.constraints.NotBlank

data class CreateAddressRequest(

    @field:NotBlank
    val city: String,

    val country: Country? = null,

    val postalCode: String? = null
)
