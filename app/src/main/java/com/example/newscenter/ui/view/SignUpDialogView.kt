package com.example.newscenter.ui.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.newscenter.db.App
import com.example.newscenter.db.User
import com.example.newscenter.ui.model.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SignUpDialog(loginViewModel: AppViewModel) {
    val dialogState by loginViewModel.dialogState.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val userDao = App.db.userDao()
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