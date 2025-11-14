package com.example.androidlab

import android.app.Application
import android.util.Log
import com.example.androidlab.data.auth.local.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        // ★ DB 파일 생성/오픈 트리거
        db.openHelper.writableDatabase
        Log.d("DB", "opened at: " + getDatabasePath("adoptlink.db").absolutePath)
    }
}