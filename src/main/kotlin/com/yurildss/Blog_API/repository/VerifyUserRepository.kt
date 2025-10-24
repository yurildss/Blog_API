package com.yurildss.Blog_API.repository

import com.yurildss.Blog_API.model.VerificationToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface VerifyUserRepository: MongoRepository<VerificationToken, ObjectId> {
    fun saveVerifyToken(verifyUser: VerificationToken)
    fun findByUserIdAndHashedToken(userId: ObjectId, hashedToken: String)
}