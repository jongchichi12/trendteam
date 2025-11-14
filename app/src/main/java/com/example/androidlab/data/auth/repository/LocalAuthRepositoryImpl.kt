package com.example.androidlab.data.auth.repository

import com.example.androidlab.core.AppResult
import com.example.androidlab.core.constants.Region
import com.example.androidlab.data.auth.local.LocalAuthDataSource
import com.example.androidlab.domain.auth.entity.User
import com.example.androidlab.domain.auth.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthRepositoryImpl @Inject constructor(
    private val local: LocalAuthDataSource
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): AppResult<User> =
        runCatching { local.signIn(email, password) }
            .fold(onSuccess = { AppResult.Success(it) }, onFailure = { AppResult.Error(it.message ?: "로그인 실패", it) })
    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        username: String,
        phone: String,
        region: Region
    ) = runCatching {
        local.signUp(email, password, displayName, username, phone, region)
    }.fold(
        onSuccess = { AppResult.Success(it) },
        onFailure = { AppResult.Error(it.message ?: "회원가입 실패", it) }
    )
    override suspend fun signOut() { local.signOut() }
    override fun currentUser() = local.currentUser()
}