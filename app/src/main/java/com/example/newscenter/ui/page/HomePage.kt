package com.example.newscenter.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newscenter.spider.Spider
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.NewsCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomePage(navController: NavHostController, viewModel: AppViewModel) {
    val news by viewModel.newsList.collectAsState()
    if (news.isEmpty()) {
        val spider = Spider()
        CoroutineScope(Dispatchers.IO).launch {
            val tempNews = spider.getNewsList().slice(0..10)
            viewModel.setNews(tempNews)
            println(tempNews)
        }
    }
    LazyColumn {

        items(news) {
            NewsCard(
                imgUrl = it.imgurl,
                title = it.title,
                source = it.source,
                category = it.category!!,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.changeNewsContent(it)
                        withContext(Dispatchers.Main) {
                            navController.navigate("news_page")
                        }
                    }
                }
            )
        }

    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//
//        val permission = listOf(
//            PMate(Manifest.permission.INTERNET, true, ""),
//        )
//        val pms = rememberPermissionMateState(permissions = permission)
//        Button(onClick = {
//            pms.start()
//        }) {
//            Text("主页", fontSize = 40.sp)
//        }
//    }
}