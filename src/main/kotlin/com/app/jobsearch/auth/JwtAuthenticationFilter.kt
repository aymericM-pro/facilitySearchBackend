package com.app.jobsearch.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        log.debug("JWT Filter triggered for {}", request.requestURI)

        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {
            try {
                val token = header.substring(7)
                log.debug("JWT token found")

                if (!jwtService.isTokenExpired(token)) {
                    val email = jwtService.extractEmail(token)
                    log.debug("JWT valid for {}", email)

                    val userDetails = userDetailsService.loadUserByUsername(email)

                    val auth = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                    SecurityContextHolder.getContext().authentication = auth
                } else {
                    log.warn("JWT expired")
                }

            } catch (e: Exception) {
                log.error("JWT processing failed: {}", e.message, e)
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }


}
