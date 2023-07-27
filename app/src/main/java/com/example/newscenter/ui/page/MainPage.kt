package com.example.newscenter.ui.page


import BottomNavigationBar
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.newscenter.Navigation
import com.example.newscenter.ui.model.AppViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(model: AppViewModel) {
    val navController = rememberNavController()
    var fabVisible by remember { mutableStateOf(false) }
    var navVisible by remember { mutableStateOf(false) }
    var liked by remember { mutableStateOf(false) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        fabVisible = destination.route == "news_page"
        navVisible = destination.route == "news_page"
    }
    val context = LocalContext.current
    TransparentSystemBars()
    Scaffold(
        topBar = { /*标题栏*/
            TopAppBar(title = {
                if (!navVisible) {
                    Text("NewsCenter")
                }
            },
                navigationIcon = {
                    if (navVisible) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }

                },
                actions = {
                    if (navVisible) {
                        IconButton(onClick = {
                            liked = !liked
                        }) {
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

                })
        },
        floatingActionButton = { /*悬浮按钮*/
            if (fabVisible) {
                FloatingActionButton(onClick = {
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_TEXT, "TODO")
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
        bottomBar = { BottomNavigationBar(navController) },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
    )
}


@Composable
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false,
        )
    }
}