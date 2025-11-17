package com.example.androidlab.data.post

import com.example.androidlab.data.local.db.PostEntity
import com.example.androidlab.domain.post.Post

fun PostEntity.toDomain(): Post = Post(
    id = id,
    authorId = authorId,
    authorName = authorName,
    content = content,
    imageUri = imageUri,
    likesCount = likesCount,
    commentsCount = commentsCount,
    createdAt = createdAt
)

fun Post.toEntity(): PostEntity = PostEntity(
    id = id,
    authorId = authorId,
    authorName = authorName,
    content = content,
    imageUri = imageUri,
    likesCount = likesCount,
    commentsCount = commentsCount,
    createdAt = createdAt
)
