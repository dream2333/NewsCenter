package com.example.newscenter.ui.page


import BottomNavigationBar
import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import com.example.newscenter.Navigation
import com.example.newscenter.db.App
import com.example.newscenter.ui.model.AppViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import com.example.newscenter.db.Favorite
import com.example.newscenter.ui.view.TransparentSystemBars
import com.example.newscenter.ui.view.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
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
                                CoroutineScope(Dispatchers.IO).launch {
                                    liked = favorDao.isUserFavor(
                                        currentNewsItem!!.title,
                                        currentUser!!.id
                                    ).isNotEmpty()
                                }
                            }
                            if (liked) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = Color(0xFFEF5350)
                                )
                            } else {
                                Icon(
                                    Icons.Filled.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color(0xFFEF5350)
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
                    FloatingActionButton(modifier = Modifier.zIndex(10000f), onClick = {
                        val intent = Intent()
                        intent.setAction(Intent.ACTION_SEND)
                        intent.putExtra(Intent.EXTRA_TEXT, currentNewsItem?.docurl)
                        intent.setType("text/plain")
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.applicationContext.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Share, contentDescription = "share"
                        )
                    }
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

