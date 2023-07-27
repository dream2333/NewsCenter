package com.example.newscenter.spider

class Spider {
    private val parser = Parser()
    private val meta = Meta()
    fun getNewsList(): List<NewsItem> {
        return parser.getNewsList(meta.categorys)
    }
    fun crawl() {
        val newsList = parser.getNewsList(meta.categorys)
        println(newsList)
        for (news in newsList) {
            news.content = parser.getContent(news.docurl)
            println(news)
            break
        }
    }
}

fun main() {
    val spider = Spider()
    spider.crawl()
}