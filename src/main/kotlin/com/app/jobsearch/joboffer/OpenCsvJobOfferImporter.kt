package com.app.jobsearch.joboffer

import com.app.jobsearch.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.joboffer.dto.JobOfferCsvRow
import com.app.jobsearch.joboffer.dto.JobOfferResponse
import com.opencsv.bean.CsvToBeanBuilder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.InputStreamReader



@Component
class OpenCsvJobOfferImporter : JobOfferCsvImporter {

    override fun parse(inputStream: InputStream): List<CreateJobOfferRequest> {

        val rows = CsvToBeanBuilder<JobOfferCsvRow>(
            InputStreamReader(inputStream)
        )
            .withType(JobOfferCsvRow::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse()

        return rows.map { it.toRequest() }
    }
}