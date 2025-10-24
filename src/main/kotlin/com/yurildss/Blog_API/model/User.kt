package com.yurildss.Blog_API.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id var id: ObjectId = ObjectId(),
    var email: String,
    var hashedPassword: String,
    var enabled: Boolean = false,
)