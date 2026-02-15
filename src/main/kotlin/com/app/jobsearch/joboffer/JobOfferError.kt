package com.app.jobsearch.joboffer

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class JobOfferError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND(code = "JOB_OFFER_NOT_FOUND", message = "Job offer not found", status = HttpStatus.NOT_FOUND),
    TITLE_REQUIRED(code = "JOB_OFFER_TITLE_REQUIRED", message = "Title is required", status = HttpStatus.BAD_REQUEST),
    COMPANY_REQUIRED(code = "JOB_OFFER_COMPANY_REQUIRED", message = "Company is required", status = HttpStatus.BAD_REQUEST),
    INVALID_SALARY_RANGE(code = "JOB_OFFER_INVALID_SALARY_RANGE", message = "Minimum salary cannot be greater than maximum salary", status = HttpStatus.BAD_REQUEST)
}
