package com.app.jobsearch.modules.joboffer

import java.net.URL

interface FileStorage {
    fun uploadPdf(bytes: ByteArray, username: String, company: String): String
    fun generateSignedUrl(objectName: String): URL
}
