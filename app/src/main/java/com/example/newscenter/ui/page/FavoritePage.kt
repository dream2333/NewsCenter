package com.example.newscenter.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newscenter.db.App
import com.example.newscenter.db.News
import com.example.newscenter.spider.NewsItem
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.FavoriteCard


@Composable
fun FavoritePage(navController: NavHostController, model: AppViewModel) {
    val favorites by model.currentFavorites.collectAsState()
    val currentUser by model.currentUser.collectAsState()
    if (currentUser != null) {
        if (favorites.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(favorites) {
                    FavoriteCard(
                        imgUrl = it!!.imgurl,
                        title = it.title,
                        source = it.source,
                        category = it.category,
                        onClick = {
                            val news = News(
                                title = it.title,
                                source = it.source,
                                category = it.category,
                                imgurl = it.imgurl,
                                content = it.content,
                                docurl = "",
                                time = it.time,
                            )
                            model.setFavorContent(news)
                            navController.navigate("news_page")
                        },
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "No favorite news",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Please login first",
                fontSize = 20.sp,
            )
        }
    }
}
