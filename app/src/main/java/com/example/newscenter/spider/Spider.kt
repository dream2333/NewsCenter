package com.example.newscenter.spider

import com.example.newscenter.db.News

class Spider {
    private val parser = Parser()
    private val meta = Meta()
    fun getNewsList(): List<News> {
        return parser.getNewsList(meta.categorys)
    }
    fun crawl() {
        val newsList = parser.getNewsList(meta.categorys)
        println(newsList)
        for (news in newsList) {
            news.content = parser.getContent(news.docurl)
            news.imgurl.replace("http://", "https://" )
            println(news)
            break
        }
    }

}

fun main() {
    val spider = Spider()
    spider.crawl()
}