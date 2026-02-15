package com.app.jobsearch.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "openai")
class OpenAiProperties {
    lateinit var apiKey: String
}