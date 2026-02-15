package com.app.jobsearch.core.error

class BusinessException(
    val error: DomainError
) : RuntimeException(error.message)