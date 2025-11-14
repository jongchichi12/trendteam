package com.example.androidlab.di

import com.example.androidlab.data.auth.repository.LocalAuthRepositoryImpl
import com.example.androidlab.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BackendBinding {
    @Binds @Singleton
    abstract fun bindAuthRepository(impl: LocalAuthRepositoryImpl): AuthRepository
}