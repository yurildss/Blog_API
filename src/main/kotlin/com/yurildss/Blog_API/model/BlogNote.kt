package com.yurildss.Blog_API.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("BlogNote")
data class BlogNote(
    @Id val id: ObjectId = ObjectId.get(),
    val ownerId: ObjectId,
    val body: String,
    val comments: List<Comments>,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
)