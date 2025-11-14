package com.example.androidlab.di

import com.example.androidlab.core.AppResult
import com.example.androidlab.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryStub @Inject constructor() : AuthRepository {
    override suspend fun signIn(email: String, password: String) =
        AppResult.Error("Firebase 미연결(Stub)")
    override suspend fun signUp(email: String, password: String, displayName: String) =
        AppResult.Error("Firebase 미연결(Stub)")
    override suspend fun signOut() {}
    override fun currentUser() = null

    override suspend fun signUp(
        email: String, password: String, displayName: String,
        username: String, phone: String, region: Region
    ) = AppResult.Error("Firebase 미연결(Stub)")

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BackendBinding {
    @Binds @Singleton
    abstract fun bindAuthRepository(impl: FirebaseAuthRepositoryStub): AuthRepository
}