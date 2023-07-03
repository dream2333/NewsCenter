package com.example.newscenter.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val username: String,
    val password: String
)


@Entity
data class News(
    @PrimaryKey val title: String,
    val commenturl: String,
    val docurl: String,
    val imgurl: String,
    val keywords: String,
    val time: String,
    var category: String,
    var content: String,
)