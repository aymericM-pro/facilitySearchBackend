package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.address.AddressService
import com.app.jobsearch.modules.address.Country
import com.app.jobsearch.modules.address.request.AddressResponse
import com.app.jobsearch.modules.address.request.CreateAddressRequest
import com.app.jobsearch.modules.address.request.UpdateAddressRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
class AddressIntegrationTest(
    @Autowired val service: AddressService
) : IntegrationTestBase() {

    @Test
    fun testCreateFull() {
        val request = fullRequest()
        val created = service.create(request)

        assertAddress(created, request)
    }

    @Test
    fun testCreateMinimal() {
        val request = CreateAddressRequest(city = "Lyon")
        val created = service.create(request)

        assertNotNull(created.id)
        assertEquals("Lyon", created.city)
        assertNull(created.country)
        assertNull(created.postalCode)
    }

    @Test
    fun testFindById() {
        val created = service.create(fullRequest())
        val found = service.findById(created.id)

        assertEquals(created.id, found.id)
        assertEquals(created.city, found.city)
        assertEquals(created.country, found.country)
        assertEquals(created.postalCode, found.postalCode)
    }

    @Test
    fun testUpdate() {
        val created = service.create(fullRequest())

        val updateRequest = UpdateAddressRequest(
            city = "Marseille",
            country = Country.FRANCE,
            postalCode = "13001"
        )

        val updated = service.update(created.id, updateRequest)

        assertEquals("Marseille", updated.city)
        assertEquals(Country.FRANCE.label, updated.country)
        assertEquals("13001", updated.postalCode)
    }

    @Test
    fun testUpdatePartial() {
        val created = service.create(fullRequest())

        val updated = service.update(created.id, UpdateAddressRequest(city = "Bordeaux"))

        assertEquals("Bordeaux", updated.city)
        assertEquals(created.country, updated.country)
        assertEquals(created.postalCode, updated.postalCode)
    }

    @Test
    fun testDelete() {
        val created = service.create(fullRequest())
        service.delete(created.id)

        assertThrows(BusinessException::class.java) {
            service.findById(created.id)
        }
    }

    @Test
    fun testNotFound() {
        assertThrows(BusinessException::class.java) {
            service.findById(UUID.randomUUID())
        }
    }

    @Test
    fun testDeleteNotFound() {
        assertThrows(BusinessException::class.java) {
            service.delete(UUID.randomUUID())
        }
    }

    @Test
    fun testUpdateNotFound() {
        assertThrows(BusinessException::class.java) {
            service.update(UUID.randomUUID(), UpdateAddressRequest(city = "Paris"))
        }
    }

    @Test
    fun testPagination() {
        repeat(3) { i -> service.create(CreateAddressRequest(city = "City$i")) }

        val page = service.findAll(PageRequest.of(0, 2))

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
        assertEquals(2, page.totalPages)
        assertEquals(0, page.number)
    }

    @Test
    fun testBlankCity() {
        assertThrows(BusinessException::class.java) {
            service.create(CreateAddressRequest(city = ""))
        }
    }

    private fun fullRequest() = CreateAddressRequest(
        city = "Paris",
        country = Country.FRANCE,
        postalCode = "75001"
    )

    private fun assertAddress(actual: AddressResponse, expected: CreateAddressRequest) {
        assertNotNull(actual.id)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.country?.label, actual.country)
        assertEquals(expected.postalCode, actual.postalCode)
    }
}
