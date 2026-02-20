package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.education.*
import com.app.jobsearch.modules.education.requests.*
import com.app.jobsearch.modules.profile.ProfileEntity
import com.app.jobsearch.modules.profile.ProfileRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Transactional
class EducationIntegrationTest(
    @Autowired val service: EducationService,
    @Autowired val profileRepository: ProfileRepository
) : IntegrationTestBase() {

    private lateinit var profile: ProfileEntity

    @BeforeEach
    fun setup() {
        profile = profileRepository.save(
            ProfileEntity(
                name = "Aymeric Maillot",
                title = "Software Engineer"
            )
        )
    }

    @Test
    fun testIntegrationCreateFull() {
        val request = fullRequest()
        val created = service.createEducation(request)
        assertEducation(created, request)
    }

    @Test
    fun testIntegrationFindById() {
        val created = service.createEducation(fullRequest())
        val found = service.getEducationById(created.id)

        assertEquals(created.id, found.id)
    }

    @Test
    fun testIntegrationUpdate() {
        val created = service.createEducation(fullRequest())

        val updateRequest = EducationUpdateRequest(
            school = "EFREI Updated",
            degree = "Master 2"
        )

        val updated = service.updateEducation(created.id, updateRequest)

        assertEquals("EFREI Updated", updated.school)
        assertEquals("Master 2", updated.degree)
    }

    @Test
    fun testIntegrationDelete() {
        val created = service.createEducation(fullRequest())
        service.deleteEducation(created.id)

        assertThrows(BusinessException::class.java) {
            service.getEducationById(created.id)
        }
    }

    @Test
    fun testIntegrationGetAll() {
        repeat(3) { service.createEducation(fullRequest()) }

        val result = service.getAllEducation()

        assertEquals(3, result.size)
    }

    @Test
    fun testIntegrationNotFound() {
        assertThrows(BusinessException::class.java) {
            service.getEducationById(UUID.randomUUID())
        }
    }

    @Test
    fun testIntegrationInvalidDateRange() {
        assertThrows(BusinessException::class.java) {
            service.createEducation(
                fullRequest().copy(
                    endDate = LocalDate.of(2010, 1, 1)
                )
            )
        }
    }

    private fun fullRequest() =
        EducationCreateRequest(
            profileId = profile.id!!,
            school = "EFREI Paris",
            degree = "Ingénieur",
            field = "Software Engineering",
            location = "Paris",
            startDate = LocalDate.of(2018, 9, 1),
            endDate = LocalDate.of(2023, 6, 30),
            logo = "efrei.png"
        )

    private fun assertEducation(
        actual: EducationResponse,
        expected: EducationCreateRequest
    ) {
        assertNotNull(actual.id)
        assertEquals(expected.profileId, actual.profileId)
        assertEquals(expected.school, actual.school)
        assertEquals(expected.degree, actual.degree)
        assertEquals(expected.field, actual.field)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.startDate, actual.startDate)
        assertEquals(expected.endDate, actual.endDate)
        assertEquals(expected.logo, actual.logo)
    }
}