package com.example.androidlab.domain.post.usecase

import com.example.androidlab.domain.post.PostRepository
import javax.inject.Inject

class LikePost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String) = repository.like(postId)
}
