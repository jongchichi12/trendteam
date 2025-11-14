package com.example.androidlab.domain.auth.repository

import com.example.androidlab.core.AppResult
import com.example.androidlab.core.constants.Region
import com.example.androidlab.domain.auth.entity.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AppResult<User>

    // ⬇️ 새 파라미터(기본값 제공 → 기존 호출부도 빌드됨)
    suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        username: String = "",
        phone: String = "",
        region: Region = Region.SEOUL
    ): AppResult<User>

    suspend fun signOut()
    fun currentUser(): User?
}