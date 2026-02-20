package com.app.jobsearch.modules.profile

import com.app.jobsearch.modules.address.Address
import com.app.jobsearch.modules.education.EducationEntity
import com.app.jobsearch.modules.experiences.ExperienceEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "profiles")
data class ProfileEntity(

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null,

    @Column(nullable = false)
    var name: String,

    var title: String? = null,

    var location: String? = null,

    var available: Boolean = true,

    var email: String? = null,
    var phone: String? = null,
    var linkedin: String? = null,
    var website: String? = null,

    @Column(length = 2000)
    var about: String? = null,

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "address_id")
    var address: Address? = null,

    @ElementCollection
    @CollectionTable(name = "profile_skills", joinColumns = [JoinColumn(name = "profile_id")])
    @Column(name = "skill")
    var skills: MutableList<String> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true)
    var experiences: MutableList<ExperienceEntity> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true)
    var educations: MutableList<EducationEntity> = mutableListOf()
)
