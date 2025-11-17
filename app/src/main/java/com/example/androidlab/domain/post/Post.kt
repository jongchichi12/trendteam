package com.example.androidlab.domain.post

data class Post(
    val id: String,
    val authorId: String,
    val authorName: String,
    val content: String,
    val imageUri: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long
)
