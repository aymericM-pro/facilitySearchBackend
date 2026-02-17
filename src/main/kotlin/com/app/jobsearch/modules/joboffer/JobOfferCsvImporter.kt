package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import java.io.InputStream

fun interface JobOfferCsvImporter {
    fun parse(inputStream: InputStream): List<CreateJobOfferRequest>
}