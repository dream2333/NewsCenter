package com.example.newscenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.page.MainPage
import com.example.newscenter.ui.theme.NewsCenterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val loginViewModel: AppViewModel by viewModels()
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