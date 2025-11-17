package com.example.androidlab.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidlab.data.auth.local.LocalAuthDataSource
import com.example.androidlab.data.auth.local.db.AppDatabase
import com.example.androidlab.data.auth.local.db.UserDao
import com.example.androidlab.data.local.db.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "adoptlink.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    android.util.Log.d("DB", "onCreate: " + context.getDatabasePath("adoptlink.db"))
                }
                override fun onOpen(db: SupportSQLiteDatabase) {
                    android.util.Log.d("DB", "onOpen: " + context.getDatabasePath("adoptlink.db"))
                }
            })
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides @Singleton
    fun provideLocalAuthDataSource(dao: UserDao): LocalAuthDataSource = LocalAuthDataSource(dao)

    @Provides
    fun providePostDao(db: AppDatabase): PostDao = db.postDao()
}
