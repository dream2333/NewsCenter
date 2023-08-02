package com.example.newscenter.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date


@Entity(
    tableName = "news",
    indices = [
        Index(value = ["title"], unique = true),
    ]
)
data class News(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val source: String,
    val imgurl: String,
    val time: String,
    var category: String,
    var content: String?,
)

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var username: String,
    var password: String,
    var email: String = "",
    var phone: String = ""
)

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = News::class, parentColumns = ["id"], childColumns = ["newsId"])
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["newsId"], unique = true)
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val newsId: Int
)

@Entity(
    tableName = "history",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = News::class, parentColumns = ["id"], childColumns = ["newsId"])
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["newsId"], unique = true)
    ]
)
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val newsId: Int,
    val date: Date = Date()
)