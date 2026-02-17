package com.app.jobsearch.auth

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

    fun register(request: AuthRequest): AuthResponse {

        if (userRepository.findByEmail(request.email) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already exists")
        }

        val user = userRepository.save(
            UserEntity(
                email = request.email,
                password = passwordEncoder.encode(request.password)!!,
                pseudo = request.pseudo
            )
        )

        return generateTokens(user)
    }

    fun login(request: AuthRequest): AuthResponse {

        val user = userRepository.findByEmail(request.email) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        return generateTokens(user)
    }

    private fun generateTokens(user: UserEntity): AuthResponse =
        AuthResponse(
            accessToken = jwtService.generateAccessToken(user),
            refreshToken = jwtService.generateRefreshToken(user)
        )
}
