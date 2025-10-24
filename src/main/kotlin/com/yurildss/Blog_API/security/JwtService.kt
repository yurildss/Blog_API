package com.yurildss.Blog_API.security

import com.mongodb.internal.time.Timeout.expiresIn
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64
import java.util.Date

@Service
class JwtService (
    @Value ("\${jwt.secret}") val jwtSecretKey: String
) {
    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecretKey))
    private val acessTokenValidyMs = 15L * 60L * 1000L
    val refreshTokenValidyMs = 30L* 24 * 60 * 60 * 1000L

    private fun generateToken(
        userId: String,
        type: String,
        expiresIn: Long
    ): String {
        val now = Date()
        val expirationDate = Date(now.time + expiresIn)
        return Jwts.builder()
            .subject(userId)
            .claim("type", type)
            .issuedAt(expirationDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateRefreshToken(userId: String): String {
        return generateToken(userId = userId, type = "refresh", expiresIn = refreshTokenValidyMs)
    }

    fun generateToken(userId: String): String {
        return generateToken(userId = userId, type = "access", expiresIn = acessTokenValidyMs)
    }

    fun generateValidToken(userId: String): String {
        return generateToken(userId = userId, type = "verify", expiresIn = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli())
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaimsFromToken(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaimsFromToken(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    fun validateVerificationToken(token: String): Boolean {
        val claims = parseAllClaimsFromToken(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "verify"
    }

    fun getUserIdFromJWT(token: String): String {
        val claims = parseAllClaimsFromToken(token) ?: throw ResponseStatusException(HttpStatusCode.valueOf(401),"Invalid JWT token")
        return claims.subject
    }

    private fun parseAllClaimsFromToken(token: String): Claims? {
        val rawToken = if(token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        }else token

        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        }catch (e: Exception){
            null
        }
    }
}