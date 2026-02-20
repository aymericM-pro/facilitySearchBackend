package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.address.Address
import com.app.jobsearch.modules.companies.CompanyEntity
import com.app.jobsearch.modules.companies.CompanyRepository
import com.app.jobsearch.modules.companies.CompanyType
import com.app.jobsearch.modules.companies.Industry
import com.app.jobsearch.modules.joboffer.ContractType
import com.app.jobsearch.modules.joboffer.JobOfferCriteria
import com.app.jobsearch.modules.joboffer.JobOfferService
import com.app.jobsearch.modules.joboffer.dto.CompanyJobOfferCount
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferResponse
import com.app.jobsearch.modules.joboffer.dto.UpdateJobOfferRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Transactional
class JobOfferIntegrationTest(
    @Autowired val service: JobOfferService,
    @Autowired val companyRepository: CompanyRepository
) : IntegrationTestBase() {

    private lateinit var company: CompanyEntity

    @BeforeEach
    fun setup() {
        company = companyRepository.save(
            CompanyEntity(
                name = "Agate IT",
                address = Address(city = "Paris"),
                companyType = CompanyType.STARTUP,
                industry = Industry.SAAS
            )
        )

    }

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
            JobOfferCriteria(),
            PageRequest.of(0, 2)
        )

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
        assertEquals(2, page.totalPages)
        assertEquals(0, page.page)
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
                    companyId = company.id!!,
                    location = null,
                    description = null,
                    skills = emptyList(),
                    remote = false,
                    salaryMin = BigDecimal(70000),
                    salaryMax = BigDecimal(50000),
                    contractType = ContractType.CDI
                )
            )
        }
    }

    @Test
    fun testFindAllByCompanyReturnsOnlyItsOffers() {
        val otherCompany = companyRepository.save(
            CompanyEntity(
                name = "Other Corp",
                address = Address(city = "Lyon"),
                companyType = CompanyType.STARTUP,
                industry = Industry.SAAS
            )
        )

        service.create(fullRequest())
        service.create(fullRequest().copy(title = "Second offer"))
        service.create(fullRequest().copy(title = "Other company offer", companyId = otherCompany.id!!))

        val result = service.findAllByCompany(company.id!!, PageRequest.of(0, 10))

        assertEquals(2, result.totalElements)
        assertTrue(result.content.all { it.companyId == company.id })
    }

    @Test
    fun testFindAllByCompanyPagination() {
        repeat(3) { i -> service.create(fullRequest().copy(title = "Offer $i")) }

        val page = service.findAllByCompany(company.id!!, PageRequest.of(0, 2))

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
        assertEquals(2, page.totalPages)
    }

    @Test
    fun testFindAllByCompanyEmpty() {
        val emptyCompany = companyRepository.save(
            CompanyEntity(
                name = "Empty Corp",
                address = Address(city = "Bordeaux"),
                companyType = CompanyType.STARTUP,
                industry = Industry.SAAS
            )
        )

        val result = service.findAllByCompany(emptyCompany.id!!, PageRequest.of(0, 10))

        assertEquals(0, result.totalElements)
        assertTrue(result.content.isEmpty())
    }

    @Test
    fun testCountByCompanyReturnsCorrectCounts() {
        val google = companyRepository.save(
            CompanyEntity(name = "Google", companyType = CompanyType.STARTUP, industry = Industry.SAAS)
        )

        service.create(fullRequest())
        service.create(fullRequest().copy(title = "Offer 2"))
        service.create(fullRequest().copy(title = "Google Offer", companyId = google.id!!))

        val result = service.countByCompany()

        val agateCount = result.first { it.companyId == company.id }
        val googleCount = result.first { it.companyId == google.id }

        assertEquals(2L, agateCount.count)
        assertEquals(1L, googleCount.count)
        // trié par count desc : Agate IT doit être en premier
        assertTrue(result.indexOf(agateCount) < result.indexOf(googleCount))
    }

    @Test
    fun testCountByCompanyEmptyReturnsEmptyList() {
        val result = service.countByCompany()
        assertTrue(result.isEmpty())
    }

    private fun fullRequest() =
        CreateJobOfferRequest(
            title = "Senior Kotlin Backend Engineer",
            companyId = company.id!!,
            location = "Paris",
            description = "Backend Kotlin, Spring Boot, Hexagonal architecture",
            skills = listOf("Kotlin", "Spring Boot", "PostgreSQL", "AWS"),
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
        assertEquals(expected.companyId, actual.companyId)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.description, actual.description)
        assertEquals(
            (expected.skills ?: emptyList()).map { it.lowercase() }.sorted(),
            actual.skills.map { it.lowercase() }.sorted()
        )

        assertEquals(expected.remote, actual.remote)
        assertEquals(expected.salaryMin, actual.salaryMin)
        assertEquals(expected.salaryMax, actual.salaryMax)
        assertEquals(expected.contractType, actual.contractType)
        assertNotNull(actual.createdAt)
    }
}
