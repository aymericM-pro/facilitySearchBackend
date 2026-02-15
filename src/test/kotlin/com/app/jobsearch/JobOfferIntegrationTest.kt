package com.app.jobsearch


import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.joboffer.ContractType
import com.app.jobsearch.joboffer.JobOfferCriteria
import com.app.jobsearch.joboffer.JobOfferMapper
import com.app.jobsearch.joboffer.JobOfferService
import com.app.jobsearch.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.joboffer.dto.JobOfferResponse
import com.app.jobsearch.joboffer.dto.UpdateJobOfferRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Transactional
class JobOfferIntegrationTest(
    @Autowired val service: JobOfferService
) : IntegrationTestBase() {

    @Test
    fun testIntegrationCreateFull() {
        val request = fullRequest()
        val created = service.create(request)
        assertJob(created, request)
    }

    @Test
    fun testIntegrationFindById() {
        val created = service.create(fullRequest())
        val found = service.findById(created.id)
        assertEquals(created.id, found.id)
    }

    @Test
    fun testIntegrationUpdate() {
        val created = service.create(fullRequest())

        val updateRequest = UpdateJobOfferRequest(
            title = "Updated Title",
            salaryMax = BigDecimal(80000)
        )

        val updated = service.update(created.id, updateRequest)

        assertEquals("Updated Title", updated.title)
        assertEquals(BigDecimal(80000), updated.salaryMax)
    }

    @Test
    fun testIntegrationDelete() {
        val created = service.create(fullRequest())
        service.delete(created.id)

        assertThrows(BusinessException::class.java) {
            service.findById(created.id)
        }
    }

    @Test
    fun testIntegrationPagination() {
        repeat(3) { service.create(fullRequest()) }

        val page = service.findAll(
            PageRequest.of(0, 2),
            JobOfferCriteria()
        )

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
    }

    @Test
    fun testIntegrationNotFound() {
        assertThrows(BusinessException::class.java) {
            service.findById(UUID.randomUUID())
        }
    }

    @Test
    fun testIntegrationInvalidSalary() {
        assertThrows(BusinessException::class.java) {
            service.create(
                CreateJobOfferRequest(
                    title = "Invalid",
                    company = "Company",
                    salaryMin = BigDecimal(70000),
                    salaryMax = BigDecimal(50000)
                )
            )
        }
    }

    private fun fullRequest() =
        CreateJobOfferRequest(
            title = "Senior Kotlin Backend Engineer",
            company = "Agate IT",
            location = "Paris",
            description = "Backend Kotlin, Spring Boot, Hexagonal architecture",
            tags = listOf("Kotlin", "Spring Boot", "PostgreSQL", "AWS"),
            remote = true,
            salaryMin = BigDecimal(55000),
            salaryMax = BigDecimal(70000),
            contractType = ContractType.CDI
        )

    private fun assertJob(
        actual: JobOfferResponse,
        expected: CreateJobOfferRequest
    ) {
        assertNotNull(actual.id)
        assertEquals(expected.title, actual.title)
        assertEquals(expected.company, actual.company)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.description, actual.description)
        assertEquals(expected.tags, actual.tags)
        assertEquals(expected.remote, actual.remote)
        assertEquals(expected.salaryMin, actual.salaryMin)
        assertEquals(expected.salaryMax, actual.salaryMax)
        assertEquals(expected.contractType, actual.contractType)
        assertNotNull(actual.createdAt)
    }
}
