package com.app.jobsearch.modules.joboffer.dto

import com.app.jobsearch.modules.joboffer.ContractType
import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal

data class JobOfferCsvRow(

    @CsvBindByName(column = "title")
    var title: String? = null,

    @CsvBindByName(column = "company")
    var companyName: String? = null,

    @CsvBindByName(column = "location")
    var location: String? = null,

    @CsvBindByName(column = "description")
    var description: String? = null,

    @CsvBindByName(column = "skills")
    var skills: String? = null,

    @CsvBindByName(column = "remote")
    var remote: Boolean? = null,

    @CsvBindByName(column = "salaryMin")
    var salaryMin: BigDecimal? = null,

    @CsvBindByName(column = "salaryMax")
    var salaryMax: BigDecimal? = null,

    @CsvBindByName(column = "contractType")
    var contractType: ContractType? = null
)
