package com.yurildss.Blog_API.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.temporal.ChronoUnit

@Document(collection = "verificationTokens")
data class VerificationToken(
    @Id var id: ObjectId = ObjectId.get(),
    val token: String,
    val userId: ObjectId,
    @Indexed(expireAfter = "0s")
    val expirationDate: Instant = Instant.now().plus(1, ChronoUnit.DAYS),
)