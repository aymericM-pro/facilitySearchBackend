package com.app.jobsearch.modules.education

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class EducationError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND(code = "EDUCATION_NOT_FOUND", message = "Education not found", status = HttpStatus.NOT_FOUND),
    PROFILE_NOT_FOUND(code = "EDUCATION_PROFILE_NOT_FOUND", message = "Profile not found", status = HttpStatus.NOT_FOUND),
    SCHOOL_REQUIRED(code = "EDUCATION_SCHOOL_REQUIRED", message = "School is required", status = HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE(code = "EDUCATION_INVALID_DATE_RANGE", message = "End date cannot be before start date", status = HttpStatus.BAD_REQUEST)
}