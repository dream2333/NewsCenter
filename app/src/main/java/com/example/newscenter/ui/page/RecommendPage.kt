package com.example.newscenter.ui.page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newscenter.R
import com.example.newscenter.db.App
import com.example.newscenter.db.News
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.FavoriteCard
import com.example.newscenter.ui.view.NoItemPlaceholder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RecommendPage(navController: NavHostController, model: AppViewModel) {
    val recommends by model.recommends.collectAsState()
    val currentUser by model.currentUser.collectAsState()
    val context = LocalContext.current
    //    没有登录则按照游客推荐
    var username = "anonymous"
    if (currentUser != null) {
        username = currentUser!!.username
    }
    val sharedPreferences = context.getSharedPreferences("${username}_prefs", Context.MODE_PRIVATE)
    val categorys = arrayOf(
        "科技",
        "国际",
        "体育",
        "中国",
        "财经",
    )
    //    没有推荐权重的分类，按照权重1随机推荐
    //    有推荐权重的分类，按照权重随机推荐
    if (recommends.isEmpty() && currentUser != null) {
        var totalWeight = 0
        categorys.forEach { category ->
            totalWeight += sharedPreferences.getInt(category, 1)
        }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                categorys.forEach { category ->
                    val newsList = App.db.newsDao().getByCategory(category).shuffled()
                    val weight = sharedPreferences.getInt(category, 1)
                    val length = weight * 20 / totalWeight
                    //添加随机推荐
                    val tempRecommends = newsList.subList(0, length)
                    model.addRecommends(tempRecommends)
                }
//                Log.i("recommends",recommends.toString())
                Log.i("recommends",totalWeight.toString())
                model.shuffleRecommends()

            }
        }
    }
    if (currentUser != null) {
        if (recommends.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(recommends) {
                    FavoriteCard(
                        imgUrl = it.imgurl,
                        title = it.title,
                        onClick = {
                            val news = News(
                                title = it.title,
                                source = it.source,
                                category = it.category,
                                imgurl = it.imgurl,
                                content = it.content,
                                docurl = it.docurl,
                                time = it.time,
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                model.setNewsContent(news)
                                withContext(Dispatchers.Main) {
                                    navController.navigate("news_page")
                                }
                            }
                        },
                    )
                }
            }
        } else {
            NoItemPlaceholder(R.drawable.nofavorites, "No Recommend")
        }
    } else {
        NoItemPlaceholder(R.drawable.login_hint, "Please Login")
    }
}
