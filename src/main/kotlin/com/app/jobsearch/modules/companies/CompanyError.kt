package com.app.jobsearch.modules.companies

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class CompanyError(override val code: String, override val message: String, override val status: HttpStatus) : DomainError {
    NOT_FOUND("COMPANY_NOT_FOUND", "Company not found", HttpStatus.NOT_FOUND),
    NAME_REQUIRED("COMPANY_NAME_REQUIRED", "Company name is required", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS("COMPANY_ALREADY_EXISTS", "Company already exists", HttpStatus.CONFLICT)
}