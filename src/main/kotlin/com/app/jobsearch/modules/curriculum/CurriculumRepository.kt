package com.app.jobsearch.modules.curriculum

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CurriculumRepository : JpaRepository<CurriculumEntity, UUID> {
    fun findAllByUserName(userName: String): List<CurriculumEntity>
    fun findAllByJobTitleAndCompany(jobTitle: String, company: String): List<CurriculumEntity>
    fun findAllByUserNameAndJobTitleAndCompany(userName: String, jobTitle: String, company: String): List<CurriculumEntity>
}
