package com.app.jobsearch.integrations

import com.app.jobsearch.core.error.BusinessException
import com.app.jobsearch.modules.skills.SkillService
import com.app.jobsearch.modules.skills.request.CreateSkillRequest
import com.app.jobsearch.modules.skills.request.UpdateSkillRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
class SkillIntegrationTest(
    @Autowired val service: SkillService
) : IntegrationTestBase() {

    @Test
    fun testCreate() {
        val request = CreateSkillRequest(name = "Kotlin")
        val created = service.create(request)

        assertNotNull(created.id)
        assertEquals("kotlin", created.name)
    }

    @Test
    fun testFindById() {
        val created = service.create(CreateSkillRequest("Spring"))
        val found = service.findById(created.id)

        assertEquals(created.id, found.id)
        assertEquals("spring", found.name)
    }

    @Test
    fun testUpdate() {
        val created = service.create(CreateSkillRequest("Vue"))

        val updateRequest = UpdateSkillRequest(name = "VueJS")
        val updated = service.update(created.id, updateRequest)

        assertEquals("vuejs", updated.name)
    }

    @Test
    fun testDelete() {
        val created = service.create(CreateSkillRequest("Docker"))
        service.delete(created.id)

        assertThrows(BusinessException::class.java) {
            service.findById(created.id)
        }
    }

    @Test
    fun testPagination() {
        repeat(3) {
            service.create(CreateSkillRequest("Skill$it"))
        }

        val page = service.findAll(PageRequest.of(0, 2))

        assertEquals(2, page.content.size)
        assertEquals(3, page.totalElements)
    }

    @Test
    fun testNotFound() {
        assertThrows(BusinessException::class.java) {
            service.findById(UUID.randomUUID())
        }
    }

    @Test
    fun testDuplicateSkill() {
        service.create(CreateSkillRequest("Java"))

        assertThrows(BusinessException::class.java) {
            service.create(CreateSkillRequest("java"))
        }
    }

    @Test
    fun testBlankName() {
        assertThrows(BusinessException::class.java) {
            service.create(CreateSkillRequest(""))
        }
    }
}
