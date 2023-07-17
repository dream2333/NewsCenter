package com.example.newscenter.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUsers(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE username = :username ")
    fun getUser(username:String): List<User>
}

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNews(vararg news: News)

    @Update
    fun updateContent(vararg news: News)

    @Delete
    fun delete(news: News)

    @Query("SELECT * FROM news")
    fun getAll(): List<News>
}