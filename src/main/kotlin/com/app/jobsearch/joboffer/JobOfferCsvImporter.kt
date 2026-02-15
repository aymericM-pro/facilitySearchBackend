package com.app.jobsearch.joboffer

import com.app.jobsearch.joboffer.dto.CreateJobOfferRequest
import java.io.InputStream

fun interface JobOfferCsvImporter {
    fun parse(inputStream: InputStream): List<CreateJobOfferRequest>
}