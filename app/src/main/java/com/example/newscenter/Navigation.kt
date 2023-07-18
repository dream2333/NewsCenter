package com.example.newscenter

import LoginView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newscenter.ui.model.LoginViewModel
import com.example.newscenter.ui.page.FavoritePage
import com.example.newscenter.ui.page.HomePage
import com.example.newscenter.ui.page.MainPage

@Composable
fun Navigation(navController:NavHostController,loginViewModel: LoginViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home_page"
    ) {
        composable("home_page") {
            HomePage()
        }
        composable("login_page") {
            LoginView(navController,loginViewModel)
        }
        composable("favorite_page") {
            FavoritePage(navController,loginViewModel)
        }
    }
}
