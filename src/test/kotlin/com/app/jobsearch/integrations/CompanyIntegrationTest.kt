package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.companies.CompanyService
import com.app.jobsearch.modules.companies.CompanyType
import com.app.jobsearch.modules.companies.Industry
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
        assertEquals(created.industry, found.industry)
        assertEquals(created.companyType, found.companyType)
    }

    @Test
    fun testUpdate() {
        val created = service.create(fullRequest())

        val updateRequest = UpdateCompanyRequest(
            name = "Updated Company",
            city = "Marseille",
            companyType = CompanyType.STARTUP,
            industry = Industry.FINTECH
        )

        val updated = service.update(created.id, updateRequest)

        assertEquals("Updated Company", updated.name)
        assertEquals("Marseille", updated.city)
        assertEquals(CompanyType.STARTUP, updated.companyType)
        assertEquals(Industry.FINTECH, updated.industry)
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
    fun testDuplicateCompany() {
        service.create(fullRequest())

        assertThrows(BusinessException::class.java) {
            service.create(fullRequest())
        }
    }

    @Test
    fun testBlankName() {
        assertThrows(BusinessException::class.java) {
            service.create(
                CreateCompanyRequest(
                    name = "",
                    city = "Paris",
                    companyType = CompanyType.STARTUP,
                    industry = Industry.FINTECH
                )
            )
        }
    }

    private fun fullRequest() =
        CreateCompanyRequest(
            name = "Agate IT",
            city = "Paris",
            companyType = CompanyType.STARTUP,
            industry = Industry.SAAS
        )

    private fun assertCompany(actual: CompanyResponse, expected: CreateCompanyRequest) {
        assertNotNull(actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.companyType, actual.companyType)
        assertEquals(expected.industry, actual.industry)
        assertNotNull(actual.createdAt)
    }
}
