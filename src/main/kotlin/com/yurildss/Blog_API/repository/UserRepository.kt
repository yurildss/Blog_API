package com.yurildss.Blog_API.repository

import com.yurildss.Blog_API.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, ObjectId> {
    fun findUserByEmail(email: String): User?
    fun enableUserById(userId: ObjectId, enabled: Boolean)
}