package com.example.newscenter

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
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginView(loginViewModel)
                }
            }
            SignUpDialog(loginViewModel)
        }
    }

    fun createDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "news-center"
        ).build()
    }

    @Composable
    fun LoginView(model: LoginViewModel) {
        val focusManager = LocalFocusManager.current
        val username by model.username.collectAsState()
        val password by model.password.collectAsState()
        val maxLength = 36
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    if (username.length < maxLength) {
                        model.onNameChange(it)
                    }
                },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (username.length < maxLength) {
                        model.onPassChange(it)
                    }
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    // 点击完成回调到此
                    focusManager.clearFocus()
                }),
            )
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val users = userDao.getUser(username)
                        if (users.isEmpty()) {
                            model.changeDialogState()
                        } else if (users[0].password == password) {
                            Log.i("登录",users[0].toString())
                        }
                    }
                },
                modifier = Modifier.padding(24.dp)
            )
            {
                Text("Login")
            }

        }
    }

    @Composable
    fun SignUpDialog(loginViewModel: LoginViewModel) {
        val dialogState by loginViewModel.dialogState.collectAsState()
        val username by loginViewModel.username.collectAsState()
        val password by loginViewModel.password.collectAsState()
        if (dialogState) {
            AlertDialog(
                onDismissRequest = {
                    loginViewModel.changeDialogState()
                },
                title = { Text(text = "Sign Up") },
                //显示有关对话框目的的详细信息的文本。提供的文本样式默认为 Typography.body1
                text = {
                    Text(
                        "This account has not yet registered, register now?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val user = User(username,password)
                                userDao.insertUsers(user)
                            }
                            loginViewModel.changeDialogState()
                        }
                    ) {
                        Text("Yes")
                    }
                },
                //用于关闭对话框的按钮。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置,null则不显示
                dismissButton = {
                    TextButton(
                        onClick = {
                            loginViewModel.changeDialogState()
                        }
                    ) {
                        Text("No")
                    }
                },

                properties = DialogProperties(
                    //是否可以通过按下后退按钮来关闭对话框。 如果为 true，按下后退按钮将调用 onDismissRequest。
                    dismissOnBackPress = true,
                    //是否可以通过在对话框边界外单击来关闭对话框。 如果为 true，单击对话框外将调用 onDismissRequest
                    dismissOnClickOutside = true,
                    //用于在对话框窗口上设置 WindowManager.LayoutParams.FLAG_SECURE 的策略。
                    securePolicy = SecureFlagPolicy.Inherit,
                    //对话框内容的宽度是否应限制为平台默认值，小于屏幕宽度。
                    usePlatformDefaultWidth = true
                )
            )
        }
    }

}