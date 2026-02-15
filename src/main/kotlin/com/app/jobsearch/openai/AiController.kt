package com.app.jobsearch.openai

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ai")
class AiController(
    private val openAiService: OpenAiService
) {

    @PostMapping("/target-line")
    fun generateTargetLine(
        @RequestParam jobTitle: String,
        @RequestParam description: String
    ): Map<String, String> {
        val targetLine = openAiService.generateTargetLine(jobTitle, description)
        return mapOf("targetLine" to targetLine)
    }
}
