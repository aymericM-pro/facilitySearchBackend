package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.profile.ProfileService
import com.app.jobsearch.modules.profile.requests.ProfileCreateRequest
import com.app.jobsearch.modules.profile.requests.ProfileResponse
import com.app.jobsearch.modules.profile.requests.ProfileUpdateRequest
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


@Transactional
class ProfileIntegrationTest(
    @Autowired val service: ProfileService
) : IntegrationTestBase() {

    @Test
    fun testIntegrationCreateFull() {
        val request = fullRequest()
        val created = service.create(request)
        assertProfile(created, request)
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
            fullUpdateRequest()
        )

        assertEquals("Updated Name", updated.name)
    }

    @Test
    fun testIntegrationDelete() {
        val created = service.create(fullRequest())
        service.delete(created.id)

        assertThrows(BusinessException::class.java) {
            service.findById(created.id)
        }
    }

    private fun fullRequest() =
        ProfileCreateRequest(
            name = "Aymeric Maillot",
            title = "Software Engineer",
            location = "Paris",
            available = true,
            email = "aymeric@mail.com",
            phone = "0600000000",
            linkedin = "linkedin.com/in/aymeric",
            website = "aymeric.dev",
            about = "Backend Kotlin Developer",
            skills = listOf("Kotlin", "Spring Boot")
        )

    private fun fullUpdateRequest() =
        ProfileUpdateRequest(
            name = "Updated Name",
            title = null,
            location = null,
            available = null,
            email = null,
            phone = null,
            linkedin = null,
            website = null,
            about = null,
            skills = null
        )

    private fun assertProfile(
        actual: ProfileResponse,
        expected: ProfileCreateRequest
    ) {
        assertNotNull(actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.title, actual.title)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.available, actual.available)
        assertEquals(expected.email, actual.email)
        assertEquals(expected.phone, actual.phone)
        assertEquals(expected.linkedin, actual.linkedin)
        assertEquals(expected.website, actual.website)
        assertEquals(expected.about, actual.about)
        assertEquals(expected.skills.sorted(), actual.skills.sorted())
    }
}