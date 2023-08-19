package com.example.newscenter.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newscenter.spider.NewsItem

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY time DESC")
    fun getAll(): List<News>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getById(id: Int): List<News>
    @Query("SELECT * FROM news WHERE category = :category ORDER BY time DESC")
    fun getByCategory(category: String): List<News>

    @Query("UPDATE news SET content = :content WHERE title = :title")
    fun update(title: String,content:String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg news: News)

    @Delete
    fun delete(vararg news: News)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Int): List<User>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getByName(username: String): List<User>

    @Insert
    fun insert(vararg user: User)

    @Update
    fun update(vararg user: User)

    @Delete
    fun delete(vararg user: User)
}

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun getByUserId(userId: Int): List<Favorite>

    @Query("SELECT * FROM favorites WHERE title = :title and userId = :userId")
    fun isUserFavor(title: String, userId: Int): List<Favorite>

    @Insert
    fun insert(vararg favorite: Favorite)

    @Delete
    fun delete(vararg favorite: Favorite)

    @Query("Delete FROM favorites WHERE title = :title ")
    fun deleteByTitle(title: String)
}

//@Dao
//interface HistoryDao {
//    @Query("SELECT * FROM history WHERE userId = :userId ORDER BY date DESC")
//    fun getByUserId(userId: Int): List<History>
//
////    @Query("SELECT * FROM history WHERE newsId = :newsId ORDER BY date DESC")
////    fun getByNewsId(newsId: Int): List<History>
//
//    @Insert
//    fun insert(vararg history: History)
//
//    @Delete
//    fun delete(vararg history: History)
//}
