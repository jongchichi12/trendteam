package com.example.androidlab.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "posts",
    indices = [Index("createdAt")]
)
data class PostEntity(
    @PrimaryKey val id: String,
    val authorId: String,
    val authorName: String,
    val content: String,
    val imageUri: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long
)
