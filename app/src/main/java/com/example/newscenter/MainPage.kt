package com.example.newscenter


import BottomNavigationBar
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.newscenter.db.App
import com.example.newscenter.ui.model.AppViewModel
import kotlinx.coroutines.launch
import com.example.newscenter.db.Favorite
import com.example.newscenter.ui.view.ShareButton
import com.example.newscenter.ui.view.TransparentSystemBars
import com.example.newscenter.ui.view.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(model: AppViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var fabVisible by remember { mutableStateOf(false) }
    var navVisible by remember { mutableStateOf(false) }
    var liked by remember { mutableStateOf(false) }
    val currentNewsItem by model.currentNews.collectAsState()
    val currentUser by model.currentUser.collectAsState()
    navController.addOnDestinationChangedListener { _, destination, _ ->
        fabVisible = destination.route == "news_page"
        navVisible = destination.route == "news_page"
    }
    val context = LocalContext.current
    val favorDao = App.db.favoriteDao()
    val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    TransparentSystemBars()

    Box {
        Scaffold(
            topBar = { /*标题栏*/
                TopAppBar(title = {
                    if (!navVisible) {
                        Text("NewsCenter")
                    }
                }, navigationIcon = {
                    if (navVisible) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                }, actions = {
                    //右上角按钮，此处逻辑较为复杂，需要判断是否登录，是否已经收藏，修改权重
                    if (navVisible && currentUser != null) {
                        IconButton(onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (favorDao.isUserFavor(
                                        currentNewsItem!!.title,
                                        currentUser!!.id
                                    ).isEmpty()
                                ) {
                                    val favorite = Favorite(
                                        userId = currentUser!!.id,
                                        title = currentNewsItem!!.title,
                                        source = currentNewsItem!!.source,
                                        imgurl = currentNewsItem!!.imgurl,
                                        time = currentNewsItem!!.time,
                                        category = currentNewsItem!!.category!!,
                                        content = currentNewsItem!!.content
                                    )
                                    favorDao.insert(favorite)
                                    val weight = sharedPreferences.getInt(favorite.category, 1)
                                    editor.putInt(favorite.category, weight + 3).apply()
                                    liked = true
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Add to favorites list successfully!",
                                            null,
                                            false,
                                            SnackbarDuration.Short
                                        )
                                    }
                                } else {
                                    liked = false
                                    favorDao.deleteByTitle(currentNewsItem!!.title)
                                    val weight =
                                        sharedPreferences.getInt(currentNewsItem!!.category, 1)
                                    editor.putInt(currentNewsItem!!.category, weight - 3).apply()
                                }

                                CoroutineScope(Dispatchers.IO).launch {
                                    val temp = App.db
                                        .favoriteDao()
                                        .getByUserId(currentUser!!.id)
                                    model.onFavoritesChange(temp.toMutableList())

                                }
                            }
                        }) {
                            if (currentNewsItem != null) {
                                LaunchedEffect(Unit) {
                                    withContext(Dispatchers.IO) {
                                        liked = favorDao.isUserFavor(
                                            currentNewsItem!!.title,
                                            currentUser!!.id
                                        ).isNotEmpty()
                                    }
                                }
                            }
                            if (liked) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = Color(0xFFEC407A)
                                )

                            } else {
                                Icon(
                                    Icons.Filled.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color(0xFFEC407A)
                                )
                            }
                        }
                    }
                    if (!navVisible) {
                        WeatherView()
                    }
                })
            },
            floatingActionButton = { /*悬浮按钮*/
                if (fabVisible) {
                    ShareButton(currentNewsItem!!.docurl)
                }
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController, model)
                }
            },
            bottomBar = { BottomNavigationBar(navController, model) },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )
    }
}


