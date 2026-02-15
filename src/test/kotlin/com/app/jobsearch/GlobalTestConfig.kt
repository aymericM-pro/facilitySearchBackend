package com.app.jobsearch


import com.google.cloud.storage.Storage
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class GlobalTestConfig {

    @Bean
    fun storage(): Storage =
        Mockito.mock(Storage::class.java)
}