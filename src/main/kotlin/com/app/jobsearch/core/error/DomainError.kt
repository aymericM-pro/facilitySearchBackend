package com.app.jobsearch.core.error

import org.springframework.http.HttpStatus

interface DomainError {
    val code: String
    val message: String
    val status: HttpStatus
}
