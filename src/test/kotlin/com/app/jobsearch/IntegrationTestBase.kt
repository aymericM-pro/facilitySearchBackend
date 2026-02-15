package com.app.jobsearch

import com.app.jobsearch.openai.OpenAiService
import com.google.cloud.storage.Storage
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Use a single instance of the test class to share state across tests
abstract class IntegrationTestBase {

    @MockitoBean
    lateinit var storage: Storage

    @MockitoBean
    lateinit var openAiService: OpenAiService
}