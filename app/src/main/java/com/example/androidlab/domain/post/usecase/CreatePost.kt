package com.example.androidlab.domain.post.usecase

import com.example.androidlab.domain.post.Post
import com.example.androidlab.domain.post.PostRepository
import java.util.UUID
import javax.inject.Inject

class CreatePost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        authorId: String,
        authorName: String,
        content: String
    ) {
        val now = System.currentTimeMillis()
        val post = Post(
            id = UUID.randomUUID().toString(),
            authorId = authorId,
            authorName = authorName,
            content = content,
            createdAt = now
        )
        repository.create(post)
    }
}
