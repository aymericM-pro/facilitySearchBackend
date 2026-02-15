package com.app.jobsearch

import com.google.cloud.storage.Storage
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@TestConfiguration
@Profile("test")
class TestGcpConfig {

    @Bean
    fun storage(): Storage {
            return Mockito.mock(Storage::class.java)
    }
}
