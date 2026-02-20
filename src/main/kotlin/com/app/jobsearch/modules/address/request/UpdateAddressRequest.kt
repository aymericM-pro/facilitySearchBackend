package com.app.jobsearch.modules.address.request

import com.app.jobsearch.modules.address.Country

data class UpdateAddressRequest(
    val city: String? = null,
    val country: Country? = null,
    val postalCode: String? = null
)
