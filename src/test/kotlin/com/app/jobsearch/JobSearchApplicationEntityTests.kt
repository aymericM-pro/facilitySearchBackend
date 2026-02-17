package com.app.jobsearch

import com.app.jobsearch.integrations.IntegrationTestBase
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class JobSearchApplicationEntityTests : IntegrationTestBase() {

    @Test
    fun contextLoads() {}
}