package com.app.jobsearch.core.services

import com.app.jobsearch.modules.education.requests.EducationResponse
import com.app.jobsearch.modules.experiences.requests.ExperienceResponse
import com.app.jobsearch.modules.profile.requests.ProfileResponse
import org.springframework.stereotype.Service
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.context.Context
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import java.io.ByteArrayOutputStream

@Service
class PdfService(
    private val templateEngine: SpringTemplateEngine
) {

    fun generateCv(profile: ProfileResponse, experiences: List<ExperienceResponse>, educations: List<EducationResponse>): ByteArray {
        val context = Context()
        context.setVariable("profile", profile)
        context.setVariable("experiences", experiences)
        context.setVariable("educations", educations)

        val html = templateEngine.process("cv-template", context)

        val outputStream = ByteArrayOutputStream()
        val renderer = PdfRendererBuilder()
        renderer.withHtmlContent(html, null)
        renderer.toStream(outputStream)
        renderer.run()

        return outputStream.toByteArray()
    }
}
