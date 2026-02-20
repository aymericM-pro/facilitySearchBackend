package com.app.jobsearch.modules.experiences

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class ExperienceError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND("EXPERIENCE_NOT_FOUND", "Experience not found", HttpStatus.NOT_FOUND),
    PROFILE_NOT_FOUND("EXPERIENCE_PROFILE_NOT_FOUND", "Profile not found", HttpStatus.NOT_FOUND),
    ROLE_REQUIRED("EXPERIENCE_ROLE_REQUIRED", "Role is required", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE("EXPERIENCE_INVALID_DATE_RANGE", "End date cannot be before start date", HttpStatus.BAD_REQUEST)
}