package com.example.newscenter.spider

import com.example.newscenter.db.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.jsoup.Jsoup

class Parser {
    private val client = HttpClient()


    fun getNewsList(categories: List<Pair<String,String>>): List<News> {
        //正则表达式提取新闻列表页的json数据
        val pattern = """data_callback[(]([\s\S]*)[)]""".toRegex()
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        @OptIn(ExperimentalStdlibApi::class)
        val adapter = moshi.adapter<List<News>>()
        val newsList = mutableListOf<News>()
        for (category in categories) {
            val content = client.get(category.first, mapOf(), "GBK")
            val json = pattern.find(content)?.groupValues?.get(1)
            val news = json?.let { adapter.fromJson(it) }
            news!!.forEach {
                it.category = category.second
                if (it.docurl!=""){
                    newsList.add(it)
                }
            }

        }
        return newsList
    }

    fun getContent(url: String): String {
        val mobileUrl = url.replace("www.163", "m.163")
        val html = client.get(mobileUrl, mapOf(), "utf-8")
        val doc = Jsoup.parse(html)
        val xpathContent = "//section[@class='article-body js-article-body']/p[@id]"
        val content = doc.selectXpath(xpathContent).eachText().joinToString("\n\n")
        return content
    }

    fun getRecommend(url: String): List<String> {
        val mobileUrl = url.replace("www.163", "m.163")
        val html = client.get(mobileUrl, mapOf(), "utf-8")
        val doc = Jsoup.parse(html)
        val xpathRecommend = "//div[@class='recommend']/div[@class='list']/ul/li/a"
        val recommend = doc.selectXpath(xpathRecommend).eachAttr("href")
        return recommend
    }

}
