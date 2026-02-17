package com.app.jobsearch.auth

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    private val log = LoggerFactory.getLogger(AuthService::class.java)

    fun register(request: RegisterRequest): AuthResponse {

        log.info("Register attempt for email={}", request.email)

        if (userRepository.findByEmail(request.email) != null) {
            log.warn("Register failed: email already exists ({})", request.email)
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")
        }

        val encoded = passwordEncoder.encode(request.password)!!
        log.debug("Password encoded for {}", request.email)

        val user = userRepository.save(
            UserEntity(
                email = request.email,
                password = encoded,
                pseudo = request.pseudo
            )
        )

        log.info("User registered successfully: {}", request.email)

        return generateTokens(user)
    }

    fun login(request: LoginRequest): AuthResponse {

        log.info("Login attempt for email={}", request.email)

        val user = userRepository.findByEmail(request.email)
            ?: run {
                log.warn("Login failed: user not found ({})", request.email)
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
            }

        val matches = passwordEncoder.matches(request.password, user.password)
        log.debug("Password match result for {} = {}", request.email, matches)

        if (!matches) {
            log.warn("Login failed: bad password ({})", request.email)
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        log.info("Login successful for {}", request.email)

        return generateTokens(user)
    }

    private fun generateTokens(user: UserEntity): AuthResponse {
        log.debug("Generating JWT tokens for {}", user.email)

        return AuthResponse(
            accessToken = jwtService.generateAccessToken(user),
            refreshToken = jwtService.generateRefreshToken(user)
        )
    }
}
