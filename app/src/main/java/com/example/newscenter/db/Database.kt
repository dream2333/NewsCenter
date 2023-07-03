package com.example.newscenter.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, News::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
}

