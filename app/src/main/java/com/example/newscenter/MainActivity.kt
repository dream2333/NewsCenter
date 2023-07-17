package com.example.newscenter

import LoginView
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.room.Room
import com.example.newscenter.db.AppDatabase
import com.example.newscenter.db.User
import com.example.newscenter.db.UserDao
import com.example.newscenter.spider.Spider
import com.example.newscenter.ui.model.LoginViewModel
import com.example.newscenter.ui.theme.NewsCenterTheme
import com.example.newscenter.ui.view.SignUpDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private val spider = Spider()
    override fun onCreate(savedInstanceState: Bundle?) {
        val loginViewModel: LoginViewModel by viewModels()
        super.onCreate(savedInstanceState)
        db = createDatabase()
        userDao = db.userDao()
        setContent {
            NewsCenterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                ComposeNavigation(loginViewModel = loginViewModel, userDao = userDao)
                }
            }
            SignUpDialog(loginViewModel,userDao)
        }
    }

    fun createDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "news-center"
        ).build()
    }


}