package com.app.jobsearch.modules.address

import org.hibernate.annotations.UuidGenerator
import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "addresses")
data class Address(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var city: String,

    var country: String? = null,

    var postalCode: String? = null
)