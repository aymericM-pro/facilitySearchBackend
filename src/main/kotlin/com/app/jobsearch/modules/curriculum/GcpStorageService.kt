package com.app.jobsearch.modules.curriculum

import com.app.jobsearch.core.config.GcpProperties
import com.app.jobsearch.joboffer.FileStorage
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.springframework.stereotype.Service
import java.net.URL
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class GcpStorageAdapter(
    private val storage: Storage,
    private val properties: GcpProperties
) : FileStorage {

    private fun sanitize(value: String): String =
        value.replace("[^a-zA-Z0-9_-]".toRegex(), "_")

    private fun buildStoragePath(username: String, company: String): String {
        val safeUser = sanitize(username)
        val safeCompany = sanitize(company)
        val uuid = UUID.randomUUID().toString()
        return "users/$safeUser/$safeCompany/$uuid.pdf"
    }

    override fun uploadPdf(bytes: ByteArray, username: String, company: String): String {
        val objectName = buildStoragePath(username, company)

        val blobInfo = BlobInfo.newBuilder(properties.bucket, objectName)
            .setContentType("application/pdf")
            .build()

        storage.create(blobInfo, bytes)
        return objectName
    }

    override fun generateSignedUrl(objectName: String): URL {
        val blobInfo = BlobInfo.newBuilder(properties.bucket, objectName).build()
        return storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature())
    }
}
