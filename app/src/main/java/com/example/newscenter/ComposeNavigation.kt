package com.example.newscenter

import LoginView
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newscenter.db.UserDao
import com.example.newscenter.ui.model.LoginViewModel
import com.example.newscenter.ui.page.HomePage

@Composable
fun ComposeNavigation(loginViewModel: LoginViewModel,userDao: UserDao) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login_page"
    ) {
        composable("login_page") {
            LoginView(navController,loginViewModel,userDao)
        }
        composable("home_page") {
            HomePage(navController,loginViewModel)
        }
    }
}
