package com.app.jobsearch.modules.skills

import com.app.jobsearch.core.error.DomainError
import org.springframework.http.HttpStatus


enum class SkillError(
    override val code: String,
    override val message: String,
    override val status: HttpStatus
) : DomainError {

    NOT_FOUND(code = "SKILL_NOT_FOUND", message = "Skill not found", status = HttpStatus.NOT_FOUND),
    NAME_REQUIRED(code = "SKILL_NAME_REQUIRED", message = "Skill name is required", status = HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS(code = "SKILL_ALREADY_EXISTS", message = "Skill already exists", status = HttpStatus.CONFLICT)
}