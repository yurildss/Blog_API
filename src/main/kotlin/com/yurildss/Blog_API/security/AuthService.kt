package com.yurildss.Blog_API.security

import com.yurildss.Blog_API.model.RefreshToken
import com.yurildss.Blog_API.model.User
import com.yurildss.Blog_API.repository.RefreshTokenRepository
import com.yurildss.Blog_API.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatusCode
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.security.MessageDigest
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    data class TokenPair(
        val accessToken: String,
        val refreshToken: String,
    )

    fun registerUser(email: String, password: String): User {
        return userRepository.save(User(
            email = email,  hashedPassword = passwordEncoder.encode(password)
        ))
    }

    fun login(email: String, password: String): TokenPair {
        val user = userRepository.findUserByEmail(email)
            ?: throw BadCredentialsException("Invalid credentials")

        if(!passwordEncoder.matches(password,user.hashedPassword)){
            throw BadCredentialsException("Invalid credentials")
        }

        val newAccessToken = jwtService.generateToken(user.id.toHexString())
        val newRefreshToken = jwtService.generateRefreshToken(user.id.toHexString())

        storeRefreshToken(user.id,newRefreshToken)

        return TokenPair(newAccessToken, newRefreshToken)
    }

    private fun storeRefreshToken(userId: ObjectId, refreshToken: String) {
        val hashed = hashToken(refreshToken)
        val expiryMs = jwtService.refreshTokenValidyMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )
    }

    @Transactional
    fun refreshToken(refreshToken: String): TokenPair {
        if(!jwtService.validateRefreshToken(refreshToken)){
            throw ResponseStatusException(HttpStatusCode.valueOf(401),"Invalid refresh token")
        }

        val userId = jwtService.getUserIdFromJWT(refreshToken)
        val user = userRepository.findById(ObjectId(userId)).orElseThrow {
            ResponseStatusException(HttpStatusCode.valueOf(401),"Invalid refresh token")
        }

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(401),"Invalid refresh token")

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)
        val newAccessToken = jwtService.generateToken(userId)
        val newRefreshToken = jwtService.generateRefreshToken(userId)

        storeRefreshToken(user.id, newRefreshToken)
        return TokenPair(newAccessToken, newRefreshToken)
    }

    private fun hashToken(token: String): String{
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}