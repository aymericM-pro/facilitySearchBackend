package com.app.jobsearch.core.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = "",
    var expiration: Long = 0,
    var refreshExpiration: Long = 0
)