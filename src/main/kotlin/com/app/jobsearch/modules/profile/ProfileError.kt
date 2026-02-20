package com.app.jobsearch.modules.profile

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus

enum class ProfileError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND("PROFILE_NOT_FOUND", "Profile not found", HttpStatus.NOT_FOUND),
    NAME_REQUIRED("PROFILE_NAME_REQUIRED", "Name is required", HttpStatus.BAD_REQUEST)
}