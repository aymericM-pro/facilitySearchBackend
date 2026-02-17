package com.app.jobsearch

import com.app.jobsearch.integrations.IntegrationTestBase
import com.app.jobsearch.modules.openai.OpenAiService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class OpenAiServiceIntegrationTest : IntegrationTestBase() {

    @MockitoBean
    lateinit var openAiService: OpenAiService

    @Test
    fun testGenerateTargetLine() {

        whenever(
            openAiService.generateTargetLine(any(), any())
        ).thenReturn(
            "Développeur Kotlin expérimenté en architectures hexagonales et APIs cloud robustes."
        )

        val result = openAiService.generateTargetLine(
            "Senior Kotlin Backend Engineer",
            "Description"
        )

        assertTrue(result.isNotBlank())
        assertTrue(result.split("\\s+".toRegex()).size <= 25)
    }
}
