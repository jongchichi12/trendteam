package com.example.androidlab.data.post

import com.example.androidlab.data.local.db.PostDao
import com.example.androidlab.domain.post.Post
import com.example.androidlab.domain.post.PostRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPostRepositoryImpl @Inject constructor(
    private val dao: PostDao
) : PostRepository {
    override fun observePosts(): Flow<List<Post>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun create(post: Post) {
        dao.insert(post.toEntity())
    }
}
