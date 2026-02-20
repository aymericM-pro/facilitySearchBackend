package com.app.jobsearch.modules.address

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface AddressRepository : JpaRepository<Address, UUID> {

    @Query("SELECT DISTINCT a.city FROM Address a ORDER BY a.city")
    fun findAllDistinctCities(): List<String>
}
