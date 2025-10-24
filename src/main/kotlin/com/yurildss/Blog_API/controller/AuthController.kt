package com.yurildss.Blog_API.controller

import com.yurildss.Blog_API.security.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    data class AuthRequest(val username: String, val password: String)
    data class RefreshRequest(val refreshToken: String)

    @PostMapping("/register")
    fun register(
        @RequestBody authRequest: AuthRequest,
    ){
        authService.registerUser(authRequest.username, authRequest.password)

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