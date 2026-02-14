package com.app.jobsearch.modules.curriculum

import com.app.jobsearch.core.config.GcpProperties
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class GcpStorageService(private val properties: GcpProperties) {

    private val storage: Storage = run {
        val credentials = ServiceAccountCredentials.fromStream(
            FileInputStream(properties.credentialsPath)
        )

        StorageOptions.newBuilder()
            .setCredentials(credentials)
            .build()
            .service
    }

    private fun sanitize(value: String): String = value.replace("[^a-zA-Z0-9_-]".toRegex(), "_")

    private fun buildStoragePath(username: String, company: String): String {
        val safeUser = sanitize(username)
        val safeCompany = sanitize(company)
        val uuid = UUID.randomUUID().toString()

        return "users/$safeUser/$safeCompany/$uuid.pdf"
    }

    fun uploadPdf(bytes: ByteArray, username: String, company: String): String {
        val objectName = buildStoragePath(username, company)

        val blobInfo = BlobInfo.newBuilder(properties.bucket, objectName)
            .setContentType("application/pdf")
            .build()

        storage.create(blobInfo, bytes)

        return objectName
    }

    fun generateSignedUrl(objectName: String): URL {
        val blobInfo = BlobInfo.newBuilder(properties.bucket, objectName).build()

        return storage.signUrl(
            blobInfo,
            15,
            TimeUnit.MINUTES,
            Storage.SignUrlOption.withV4Signature()
        )
    }
}

