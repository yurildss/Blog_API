package com.yurildss.Blog_API.repository

import com.yurildss.Blog_API.model.BlogNote
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<BlogNote, ObjectId> {
    /**
     * Get all note for a user by his [ownerId]
     */
    fun findByOwnerId(ownerId: ObjectId): List<BlogNote>

    /**
     * Get all notes independent who post
     */
    fun findAllNotes(): List<BlogNote>
}