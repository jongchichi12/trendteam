package com.example.androidlab.di

import com.example.androidlab.data.post.LocalPostRepositoryImpl
import com.example.androidlab.domain.post.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PostModule {
    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: LocalPostRepositoryImpl): PostRepository
}
