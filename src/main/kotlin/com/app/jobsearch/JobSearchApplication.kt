package com.app.jobsearch

import com.app.jobsearch.core.config.GcpProperties
import com.app.jobsearch.core.config.JwtProperties
import com.app.jobsearch.core.config.N8nProperties
import com.app.jobsearch.core.config.OpenAiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(N8nProperties::class, GcpProperties::class, OpenAiProperties::class, JwtProperties::class)
class JobSearchApplication

fun main(args: Array<String>) {
    runApplication<JobSearchApplication>(*args)
}
