package com.app.jobsearch.modules.curriculum

import com.app.jobsearch.core.config.N8nProperties
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import com.fasterxml.jackson.databind.ObjectMapper

@Service
class N8nService(
    private val properties: N8nProperties,
    private val objectMapper: ObjectMapper
) {

    private val client = OkHttpClient()

    fun trigger(request: TriggerN8nRequest, username: String, targetLine: String): N8nResponse {

        val payload = N8nPayload(
            jobTitle = request.jobTitle,
            targetLine = targetLine,
            userName = username
        )

        val json = objectMapper.writeValueAsString(payload)

        val httpRequest = Request.Builder()
            .url(properties.url)
            .addHeader("X-API-KEY", properties.apiKey)
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(httpRequest).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("n8n call failed: ${response.code}")
            }

            val body = response.body?.string() ?: throw RuntimeException("Empty response from n8n")
            return objectMapper.readValue(body, N8nResponse::class.java)
        }
    }
}
