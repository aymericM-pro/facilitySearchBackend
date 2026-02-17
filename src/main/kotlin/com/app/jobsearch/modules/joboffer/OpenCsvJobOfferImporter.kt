package com.app.jobsearch.modules.joboffer

import com.app.jobsearch.modules.companies.CompanyRepository
import com.app.jobsearch.modules.joboffer.dto.CreateJobOfferRequest
import com.app.jobsearch.modules.joboffer.dto.JobOfferCsvRow
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.InputStreamReader

@Component
class OpenCsvJobOfferImporter(
    private val companyRepository: CompanyRepository
) : JobOfferCsvImporter {

    override fun parse(inputStream: InputStream): List<CreateJobOfferRequest> {

        val rows = CsvToBeanBuilder<JobOfferCsvRow>(
            InputStreamReader(inputStream)
        )
            .withType(JobOfferCsvRow::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse()

        return rows.map { row ->

            val companyName = row.companyName
                ?: error("Missing company name")

            val company = companyRepository.findByName(companyName)
                ?: error("Company not found: $companyName")

            CreateJobOfferRequest(
                title = row.title ?: error("Missing title"),
                companyId = company.id!!,
                location = row.location,
                description = row.description,
                skills = row.skills
                    ?.split("|")
                    ?.map { it.trim() }
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),
                remote = row.remote ?: false,
                salaryMin = row.salaryMin,
                salaryMax = row.salaryMax,
                contractType = row.contractType
            )
        }
    }
}
