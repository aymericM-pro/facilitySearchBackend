package com.app.jobsearch.modules.address.request

import java.util.UUID

data class AddressResponse(
    val id: UUID,
    val city: String,
    val country: String?,
    val postalCode: String?
)
