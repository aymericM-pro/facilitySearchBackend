package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.experiences.ExperienceService
import com.app.jobsearch.modules.experiences.requests.ExperienceCreateRequest
import com.app.jobsearch.modules.experiences.requests.ExperienceResponse
import com.app.jobsearch.modules.experiences.requests.ExperienceUpdateRequest
import com.app.jobsearch.modules.profile.ProfileEntity
import com.app.jobsearch.modules.profile.ProfileRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class ExperienceIntegrationTest(
    @Autowired val service: ExperienceService,
    @Autowired val profileRepository: ProfileRepository
) : IntegrationTestBase() {

    private lateinit var profile: ProfileEntity

    @BeforeEach
    fun setup() {
        profile = profileRepository.save(
            ProfileEntity(name = "Aymeric", title = "Engineer")
        )
    }

    @Test
    fun testIntegrationCreateFull() {
        val request = fullRequest()
        val created = service.create(request)
        assertExperience(created, request)
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

        val updated = service.update(
            created.id,
            ExperienceUpdateRequest(role = "Updated Role", location = null, startDate = null, endDate = null, description = null)
        )

        assertEquals("Updated Role", updated.role)
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
    fun testIntegrationInvalidDateRange() {
        assertThrows(BusinessException::class.java) {
            service.create(
                fullRequest().copy(
                    endDate = fullRequest().startDate.minusYears(1)
                )
            )
        }
    }

    private fun fullRequest() =
        ExperienceCreateRequest(
            profileId = profile.id!!,
            companyId = null,
            role = "Backend Developer",
            location = "Paris",
            startDate = java.time.LocalDate.of(2022, 1, 1),
            endDate = java.time.LocalDate.of(2023, 1, 1),
            description = "Spring Boot, Kotlin"
        )

    private fun assertExperience(actual: ExperienceResponse, expected: ExperienceCreateRequest) {
        assertNotNull(actual.id)
        assertEquals(expected.profileId, actual.profileId)
        assertEquals(expected.role, actual.role)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.startDate, actual.startDate)
        assertEquals(expected.endDate, actual.endDate)
        assertEquals(expected.description, actual.description)
    }
}