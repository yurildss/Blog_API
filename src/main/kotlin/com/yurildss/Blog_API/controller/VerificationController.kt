package com.yurildss.Blog_API.controller

import com.yurildss.Blog_API.repository.UserRepository
import com.yurildss.Blog_API.repository.VerifyUserRepository
import com.yurildss.Blog_API.security.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class VerificationController(
    private val repository: VerifyUserRepository,
    private val userRepository: UserRepository
) {
    @GetMapping("/verify")
    fun verifyEmail(@RequestParam token: String){
        val verifyToken = repository.findByToken(token)
            ?: throw RuntimeException("Invalid token")

        if (verifyToken.expirationDate.isBefore(Instant.now())){
            throw RuntimeException("Invalid token")
        }

        val user = userRepository.findById(verifyToken.userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.enabled = true
        userRepository.save(user)
    }
}