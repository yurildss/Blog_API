package com.yurildss.Blog_API.controller

import com.yurildss.Blog_API.Email.EmailService
import com.yurildss.Blog_API.model.VerificationToken
import com.yurildss.Blog_API.repository.VerifyUserRepository
import com.yurildss.Blog_API.security.AuthService
import org.intellij.lang.annotations.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val mailSender: EmailService,
    private val verificationToken: VerifyUserRepository
) {
    data class AuthRequest(val username: String,
                           @field: jakarta.validation.constraints.Pattern(
                               regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                               message = "Password must contain at least one letter, one number and one special character, and be at least 8 characters long.")
                           val password: String)
    data class RefreshRequest(val refreshToken: String)

    @PostMapping("/register")
    fun register(
        @RequestBody authRequest: AuthRequest,
    ){
        val user = authService.registerUser(authRequest.username, authRequest.password)

        val token = java.util.UUID.randomUUID().toString()
        verificationToken.saveVerifyToken(VerificationToken(userId = user.id, token = token))

        val link = "https://myWebSite.com/api/verify?token=$token"
        mailSender.sendEmail(
            to = user.email,
            subject = "Confirm registration",
            body = "Click to confirm $link"
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest,
    ): AuthService.TokenPair{
       return authService.login(authRequest.username, authRequest.password)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody authRequest: RefreshRequest,
    ): AuthService.TokenPair{
        return authService.refreshToken(authRequest.refreshToken)
    }
}