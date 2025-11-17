package com.example.androidlab.data.auth.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidlab.data.local.db.PostDao
import com.example.androidlab.data.local.db.PostEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 3,            // ↑ posts 테이블 추가로 버전 증가
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}
