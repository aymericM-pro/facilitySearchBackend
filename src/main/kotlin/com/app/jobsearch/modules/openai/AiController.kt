package com.app.jobsearch.modules.openai

import com.app.jobsearch.modules.curriculum.request.AiRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ai")
class AiController(
    private val openAiService: OpenAiService
) {

    @PostMapping("/target-line")
    fun generateTargetLine(
        @RequestBody request: AiRequest
    ): Map<String, String> {
        val targetLine = openAiService.generateTargetLine(request.jobTitle, request.description)
        return mapOf("targetLine" to targetLine)
    }
}
