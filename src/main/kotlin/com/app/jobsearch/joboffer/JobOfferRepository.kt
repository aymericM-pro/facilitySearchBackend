package com.app.jobsearch.joboffer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface JobOfferRepository : JpaRepository<JobOffer, UUID>, JpaSpecificationExecutor<JobOffer>
