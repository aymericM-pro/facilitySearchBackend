package com.app.jobsearch.openai

import com.app.jobsearch.core.config.OpenAiProperties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service

@Service
class OpenAiService(
    private val properties: OpenAiProperties,
    private val objectMapper: ObjectMapper
) {
    private val client = OkHttpClient()

    fun generateTargetLine(jobTitle: String, description: String): String {

        val prompt = """
        Rédige UNE seule phrase professionnelle pour remplacer :

        "Je recherche un poste de développeur Fullstack (Java, Vue.js)."

        La phrase doit être adaptée à cette offre :

        Titre : $jobTitle
        Description :
        $description

        Maximum 25 mots.
        Ton professionnel.
        Pas d'explication.
    """.trimIndent()

        val payload = mapOf(
            "model" to "gpt-4.1-mini",
            "input" to prompt
        )

        val request = Request.Builder()
            .url("https://api.openai.com/v1/responses")
            .addHeader("Authorization", "Bearer ${properties.apiKey}")
            .addHeader("Content-Type", "application/json")
            .post(
                objectMapper.writeValueAsString(payload)
                    .toRequestBody("application/json".toMediaType())
            )
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("OpenAI error: ${response.code}")
            }

            val body = response.body?.string()
                ?: throw RuntimeException("Empty response")

            val json = objectMapper.readTree(body)
            return json["output"][0]["content"][0]["text"].asText()
        }
    }
}
