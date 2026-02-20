package com.app.jobsearch.integrations

import com.google.cloud.storage.Storage
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTestBase {

    @MockitoBean
    lateinit var storage: Storage
}