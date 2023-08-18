data class News(val title: String, val tags: List<String>, val likes: Int)

fun getRecommendedNews(newsList: List<News>, userTags: List<String>): List<News> {
    val recommendedNews = mutableListOf<News>()
    val tagWeights = mutableMapOf<String, Double>()
    val maxLikes = newsList.maxOfOrNull { it.likes } ?: 1

    // 计算标签权重
    for (news in newsList) {
        for (tag in news.tags) {
            val weight = news.likes.toDouble() / maxLikes
            tagWeights[tag] = tagWeights.getOrDefault(tag, 0.0) + weight
        }
    }

    // 根据标签权重推荐新闻
    for (news in newsList) {
        var score = 0.0
        for (tag in news.tags) {
            if (userTags.contains(tag)) {
                score += tagWeights.getOrDefault(tag, 0.0)
            }
        }
        if (score > 0) {
            recommendedNews.add(news.copy(title = "${news.title} (score: $score)"))
        }
    }

    // 按得分排序
    recommendedNews.sortByDescending { it.title }

    return recommendedNews
}