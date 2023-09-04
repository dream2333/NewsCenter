package com.example.newscenter.ui.view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import com.example.newscenter.R
import com.example.newscenter.db.App
import com.example.newscenter.db.User
import com.example.newscenter.ui.model.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val datas = mutableListOf(
    "科技",
    "国际",
    "体育",
    "中国",
    "财经",
)


@Composable
fun SignUpDialog(navController: NavHostController, loginViewModel: AppViewModel) {
    val dialogState by loginViewModel.dialogState.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val userDao = App.db.userDao()
    var hasShow by remember { mutableStateOf(false) }
    var selectedCategoryName: String? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                loginViewModel.closeDialog()
            },
            title = { Text(text = "Sign Up") },
            //显示有关对话框目的的详细信息的文本。提供的文本样式默认为 Typography.body1
            text = {
                Column {
                    Text(stringResource(id = R.string.register_message))
                    Spacer(modifier = Modifier.padding(12.dp))
                    Button(onClick = { hasShow = true }) {
                        if (selectedCategoryName != null) {
                            Text(text = "Default Category: $selectedCategoryName")
                        } else {
                            Text(text = "Select Default Favorite Category")
                        }
                    }
//                  此处对新账号的用户推荐进行加权处理，对用户选择的默认偏好加权5
                    DropdownMenu(
                        expanded = hasShow,
                        onDismissRequest = {
                            hasShow = false
                        },
                        modifier = Modifier.width(100.dp)
                    ) {
                        datas.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it, modifier = Modifier.padding(start = 10.dp))
                                },
                                onClick = {
                                    hasShow = false
                                    selectedCategoryName = it
                                    val sharedPreferences =
                                        context.getSharedPreferences("${username}_prefs", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putInt(selectedCategoryName, 10).apply()
                                })
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val user = User(username = username, password = password)
                            userDao.insert(user)
                            loginViewModel.onUserChange(user)
                            withContext(Dispatchers.Main) {
                                loginViewModel.closeDialog()
                                navController.navigate("user_page")
                            }
                        }
                    }
                ) { Text("Yes") }
            },
            //用于关闭对话框的按钮。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置,null则不显示
            dismissButton = {
                TextButton(
                    onClick = {
                        loginViewModel.closeDialog()
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