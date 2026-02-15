package com.app.jobsearch.joboffer.dto

import com.app.jobsearch.joboffer.ContractType
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal

data class JobOfferCsvRow(

    @CsvBindByName(column = "title")
    var title: String? = null,

    @CsvBindByName(column = "company")
    var company: String? = null,

    @CsvBindByName(column = "location")
    var location: String? = null,

    @CsvBindByName(column = "description")
    var description: String? = null,

    @CsvBindByName(column = "tags")
    var tags: String? = null,   // ← String ici

    @CsvBindByName(column = "remote")
    var remote: Boolean? = null,

    @CsvBindByName(column = "salaryMin")
    var salaryMin: BigDecimal? = null,

    @CsvBindByName(column = "salaryMax")
    var salaryMax: BigDecimal? = null,

    @CsvBindByName(column = "contractType")
    var contractType: ContractType? = null
) {

    fun toRequest(): CreateJobOfferRequest =
        CreateJobOfferRequest(
            title = title ?: error("Missing title"),
            company = company ?: error("Missing company"),
            location = location,
            description = description,
            tags = tags?.split("|") ?: emptyList(),
            remote = remote ?: false,
            salaryMin = salaryMin,
            salaryMax = salaryMax,
            contractType = contractType
        )
}
