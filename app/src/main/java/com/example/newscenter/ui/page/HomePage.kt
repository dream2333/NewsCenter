package com.example.newscenter.ui.page


import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newscenter.db.App
import com.example.newscenter.spider.Meta
import com.example.newscenter.spider.Spider
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.NewsCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun HomePage(navController: NavHostController, viewModel: AppViewModel) {
    val news by viewModel.newsList.collectAsState()
    val categorys = Meta().categorys
    val context = LocalContext.current
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    var username = "anonymous"
    if (currentUser != null) {
        username = currentUser!!.username
    }
    val sharedPreferences =
        context.getSharedPreferences("${username}_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()


    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp
        ) {
            categorys.forEachIndexed { index, _categorys ->
                Tab(
                    text = { Text(_categorys.second) },
                    selected = selectedTabIndex == index,
                    onClick = { viewModel.setTab(index) }
                )
            }
        }
        LazyColumn {
            val isNewsEmpty = news.isEmpty()
            val selected = categorys[selectedTabIndex].second
            CoroutineScope(Dispatchers.IO).launch {
                if (isNewsEmpty) {
                    val spider = Spider()
                    spider.getNewsList().forEach {
                        if (it.imgurl != "") {
                            App.db.newsDao().insert(it)
                        }
                    }
                }
                val _news = App.db.newsDao().getByCategory(selected)
                withContext(Dispatchers.Main) {
                    viewModel.setNews(_news)
                }
            }


            items(news) {
                NewsCard(
                    imgUrl = it.imgurl,
                    title = it.title,
                    source = it.source,
                    category = it.category!!,
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.setNewsContent(it)
                            withContext(Dispatchers.Main) {
                                navController.navigate("news_page")
                            }
                        }
                        val categoryName = categorys[selectedTabIndex].second
                        if (currentUser != null) {
                            val weight = sharedPreferences.getInt(categoryName, 1)
                            editor.putInt(categoryName, weight + 1).apply()
                        }
                    }
                )
            }
        }
    }
}
