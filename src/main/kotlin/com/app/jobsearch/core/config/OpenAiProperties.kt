package com.app.jobsearch.core.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
data class OpenAiProperties (
    var apiKey: String
)