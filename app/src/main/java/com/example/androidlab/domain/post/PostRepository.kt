package com.example.androidlab.domain.post

import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun observePosts(): Flow<List<Post>>
    suspend fun create(post: Post)
}
