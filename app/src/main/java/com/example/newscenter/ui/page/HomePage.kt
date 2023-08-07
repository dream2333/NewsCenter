package com.example.newscenter.ui.page


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp
        ) {
            categorys.forEachIndexed { index, _categorys ->
                Tab(
                    text = { Text(_categorys.second) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        LazyColumn {
            if (news.isEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val spider = Spider()
                    spider.getNewsList().forEach() {
                        App.db.newsDao().insert(it)
                    }
                }
            }
            val selected = categorys[selectedTabIndex].second
            CoroutineScope(Dispatchers.IO).launch {
                val _news = App.db.newsDao().getByCategory(selected)
                withContext(Dispatchers.Main) {
                    viewModel.setNews(_news)
                }
            }
            items(news) {
//                FavoriteCard(
//                    imgUrl = it.imgurl,
//                    title = it.title,
//                    source = it.source,
//                    category =it.category!!,
//                ){}
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
                    }
                )
            }
        }
    }
//    val permission = listOf(
//        PMate(Manifest.permission.INTERNET, true, "INTERNET"),
//        PMate(Manifest.permission.LOCATION_HARDWARE, true, "LOCATION_HARDWARE"),
//    )
//    val pms = rememberPermissionMateState(permissions = permission)
//    pms.start()
}
