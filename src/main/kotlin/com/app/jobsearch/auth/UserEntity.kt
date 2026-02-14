package com.app.jobsearch.auth

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(unique = true, nullable = false)
    val pseudo: String,

    val role: String = "USER"
)