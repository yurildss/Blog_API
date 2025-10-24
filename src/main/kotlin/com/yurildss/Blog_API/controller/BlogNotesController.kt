package com.yurildss.Blog_API.controller

import com.yurildss.Blog_API.model.BlogNote
import com.yurildss.Blog_API.model.Comments
import com.yurildss.Blog_API.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class BlogNotesController(private val repository: NoteRepository) {

    data class BlogNoteRequest(
        val id: String?,
        val body: String,
        val comments: List<Comments>,
    )

    data class BlogNoteResponse(
        val id: String,
        val body: String,
        val createdAt: Instant,
        val comments: List<Comments>,
    )

    @PostMapping
    fun saveNotes(body: BlogNoteRequest): BlogNoteResponse{
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String

        val note = repository.save(
            BlogNote(
                id = body.id?.let{ ObjectId(it)} ?: ObjectId.get(),
                body = body.body,
                createdAt = Instant.now(),
                comments = body.comments,
                updatedAt = Instant.now(),
                ownerId = ObjectId(ownerId)
                )
        )

        return BlogNoteResponse(
            id = note.id.toHexString(),
            body = note.body,
            comments = note.comments,
            createdAt = note.createdAt,
        )
    }

    @GetMapping
    fun findAll(): List<BlogNoteResponse>{
        return repository.findAll().map {
            BlogNoteResponse(it.id.toHexString(), it.body, it.createdAt, it.comments)
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteNoteById(@PathVariable id: String){
        val note = repository.findById(ObjectId(id)).orElseThrow{
            IllegalArgumentException("No note found with id $id")
        }
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        if(note.ownerId.toHexString() == ownerId){
            repository.deleteById(ObjectId(id))
        }
    }

}