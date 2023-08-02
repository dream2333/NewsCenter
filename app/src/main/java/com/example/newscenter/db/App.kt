package com.example.newscenter.db

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room

//一个全局单例数据库对象，生命周期围绕app
class App:Application() {
    companion object {
        lateinit var db: AppDatabase
        @SuppressLint("StaticFieldLeak")
        var sContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this

        db = Room
            .databaseBuilder(sContext as App,AppDatabase::class.java,"news-center")
            .build()
    }
}
