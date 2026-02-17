package com.app.jobsearch.modules.skills

import com.app.jobsearch.modules.skills.request.CreateSkillRequest
import com.app.jobsearch.modules.skills.request.SkillResponse
import com.app.jobsearch.modules.skills.request.UpdateSkillRequest
import org.springframework.stereotype.Component

@Component
class SkillMapper {

    fun toEntity(request: CreateSkillRequest): SkillEntity =
        SkillEntity(
            id = null,
            name = request.name.trim().lowercase()
        )

    fun toResponse(entity: SkillEntity): SkillResponse =
        SkillResponse(
            id = entity.id!!,
            name = entity.name
        )

    fun updateEntity(dto: UpdateSkillRequest, entity: SkillEntity) {
        dto.name?.let {
            entity.name = it.trim().lowercase()
        }
    }
}
