package com.app.jobsearch.core.config

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class GcpConfig(private val properties: GcpProperties) {

    @Bean
    fun storage(): Storage {
        val credentials = ServiceAccountCredentials.fromStream(
            FileInputStream(properties.credentialsPath)
        )

        return StorageOptions.newBuilder()
            .setCredentials(credentials)
            .build()
            .service
    }
}