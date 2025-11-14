package com.example.androidlab.data.auth.local.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["username"], unique = true) // 아이디 중복 방지
    ]
)
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val displayName: String,
    val passwordHash: String,
    // ⬇️ 추가
    val username: String,
    val phone: String,
    val region: String   // Region.name 으로 저장
)