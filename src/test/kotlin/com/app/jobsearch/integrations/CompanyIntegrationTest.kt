package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.companies.CompanyService
import com.app.jobsearch.modules.companies.request.CompanyResponse
import com.app.jobsearch.modules.companies.request.CreateCompanyRequest
import com.app.jobsearch.modules.companies.request.UpdateCompanyRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
class CompanyIntegrationTest(
    @Autowired val service: CompanyService
) : IntegrationTestBase() {

    @Test
    fun testCreateFull() {
        val request = fullRequest()
        val created = service.create(request)

        assertCompany(created, request)
    }

    @Test
    fun testFindById() {
        val created = service.create(fullRequest())
        val found = service.findById(created.id)

        assertEquals(created.id, found.id)
        assertEquals(created.name, found.name)
    }

    @Test
    fun testUpdate() {
        val created = service.create(fullRequest())

        val updateRequest = UpdateCompanyRequest(
            name = "Updated Company",
            city = "Marseille"
        )

        val updated = service.update(created.id, updateRequest)

        assertEquals("Updated Company", updated.name)
        assertEquals("Marseille", updated.city)
    }

    @Test
    fun testDelete() {
        val created = service.create(fullRequest())

        service.delete(created.id)

        assertThrows(BusinessException::class.java) {
            service.findById(created.id)
        }
    }

/*
   @Test
    fun testPagination() {
        repeat(3) { service.create(fullRequest()) }

        val page = service.findAll(PageRequest.of(0, 2))

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
    }
*/

    @Test
    fun testNotFound() {
        assertThrows(BusinessException::class.java) {
            service.findById(UUID.randomUUID())
        }
    }

    @Test
    fun testDuplicateCompany() {
        service.create(fullRequest())

        assertThrows(BusinessException::class.java) {
            service.create(fullRequest())
        }
    }

    @Test
    fun testBlankName() {
        assertThrows(BusinessException::class.java) {
            service.create(CreateCompanyRequest("", "Paris"))
        }
    }

    private fun fullRequest() =
        CreateCompanyRequest(
            name = "Agate IT",
            city = "Paris"
        )

    private fun assertCompany(actual: CompanyResponse, expected: CreateCompanyRequest) {
        assertNotNull(actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.city, actual.city)
        assertNotNull(actual.createdAt)
    }
}
