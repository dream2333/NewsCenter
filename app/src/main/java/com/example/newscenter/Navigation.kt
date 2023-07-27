package com.example.newscenter

import LoginView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.page.FavoritePage
import com.example.newscenter.ui.page.HomePage
import com.example.newscenter.ui.page.NewsPage

@Composable
fun Navigation(navController:NavHostController, appViewModel: AppViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home_page"
    ) {
        composable("home_page") {
            HomePage(navController,appViewModel)
        }
        composable("login_page") {
            LoginView(navController,appViewModel)
        }
        composable("favorite_page") {
            FavoritePage(navController,appViewModel)
        }
        composable("news_page") {
            NewsPage(appViewModel)
        }
    }
}
