package com.example.newscenter.spider

class Spider {
    private val parser = Parser()
    private val meta = Meta()
    fun crawl() {
        val newsList = parser.getNewsList(meta.categorys)
        for (news in newsList) {
            news.content = parser.getContent(news.docurl)
        }
    }
}

fun main() {
    val spider = Spider()
    spider.crawl()
}