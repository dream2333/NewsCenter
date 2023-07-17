package com.example.newscenter.ui.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.newscenter.ui.model.LoginViewModel

@Composable
fun HomePage(navController: NavHostController, model: LoginViewModel){
    Text(text = "HomePage")
}