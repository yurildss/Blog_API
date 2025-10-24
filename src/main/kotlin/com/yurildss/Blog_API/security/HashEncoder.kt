package com.yurildss.Blog_API.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoder {
    private val encoder = BCryptPasswordEncoder()

    fun encode(rawPassword: String): String = encoder.encode(rawPassword)

    fun matches(rawPassword: String, encodedPassword: String): Boolean = encoder.matches(rawPassword, encodedPassword)
}