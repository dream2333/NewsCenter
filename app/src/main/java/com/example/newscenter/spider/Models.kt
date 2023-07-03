package com.example.newscenter.spider


data class NewsItem(
    val docurl: String,
    val imgurl: String,
    val keywords: List<Keyword>,
    val time: String,
    val title: String,
    val source: String,
    var category: String? = null,
    var content: String? = null,
)

data class Keyword(
    val akey_link: String,
    val keyname: String
)
