package com.example.androidlab.domain.post.usecase

import com.example.androidlab.domain.post.Post
import com.example.androidlab.domain.post.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePosts @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> = repository.observePosts()
}
