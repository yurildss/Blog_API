package com.yurildss.Blog_API.model

import org.bson.types.ObjectId
import java.time.Instant

data class Comments(
    val body: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val userId: ObjectId,
)