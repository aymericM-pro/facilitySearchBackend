package com.app.jobsearch.auth

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: AuthRequest): Map<String, String> {

        if (userRepository.findByEmail(request.email) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")
        }

        val encodedPassword = passwordEncoder.encode(request.password)

        val user = userRepository.save(
            UserEntity(
                email = request.email,
                password = encodedPassword!!,
                pseudo = request.pseudo
            )
        )

        return mapOf("token" to jwtService.generateToken(user))
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: AuthRequest
    ): Map<String, String> {

        val email: String = request.email
        val password: String = request.password

        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("Invalid credentials")

        if (!passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("Invalid credentials")
        }

        return mapOf(
            "token" to jwtService.generateToken(user)
        )
    }
}

