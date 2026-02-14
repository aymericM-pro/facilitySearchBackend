package com.app.jobsearch.core.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gcp")
data class GcpProperties(
    var bucket: String = "",
    var credentialsPath: String = ""
)