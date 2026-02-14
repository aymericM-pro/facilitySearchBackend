package com.app.jobsearch.core.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "n8n.webhook")
data class N8nProperties(
    var url: String = "",
    var apiKey: String = ""
)