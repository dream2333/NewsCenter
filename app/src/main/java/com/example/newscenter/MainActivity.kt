package com.example.newscenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.example.newscenter.db.App
import com.example.newscenter.spider.Spider
import com.example.newscenter.ui.model.LoginViewModel
import com.example.newscenter.ui.page.MainPage
import com.example.newscenter.ui.theme.NewsCenterTheme

class MainActivity : ComponentActivity() {

    private val spider = Spider()
    override fun onCreate(savedInstanceState: Bundle?) {
        val loginViewModel: LoginViewModel by viewModels()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewsCenterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage(loginViewModel)

                }
            }
        }
    }


}