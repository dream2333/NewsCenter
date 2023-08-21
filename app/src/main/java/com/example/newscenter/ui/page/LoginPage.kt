import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import com.example.newscenter.R
import com.example.newscenter.db.App
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.SignUpDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginView(navController: NavHostController, model: AppViewModel) {
    val userDao = App.db.userDao()
    val focusManager = LocalFocusManager.current
    val username by model.username.collectAsState()
    val password by model.password.collectAsState()
    val maxLength = 36
    var errorDialogState by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500) // 延迟 500ms 清空
        model.onUserChange(null)
    }
    if (errorDialogState) {
        AlertDialog(
            title = { Text("Error") },
            text = { Text(stringResource(id = R.string.password_wrong)) },
            onDismissRequest = { errorDialogState = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        errorDialogState = false
                    }
                ) { Text("Yes") }
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.user_page), contentDescription = null)
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
            enabled = username.isNotEmpty() && password.isNotEmpty(),
            onClick = {
                //使用协程操作数据库
                CoroutineScope(Dispatchers.IO).launch {
                    val users = userDao.getByName(username)
                    if (users.isEmpty()) {
                        model.openDialog()
                    } else {
                        if (users[0].password == password) {
                            model.onUserChange(users[0])
                            val temp = App.db.favoriteDao().getByUserId(users[0].id)
                            model.onFavoritesChange(temp.toMutableList())
                            //此处需要切换回主线程
                            withContext(Dispatchers.Main) {
                                navController.navigate("user_page")
                            }
                        } else {
                            model.closeDialog()
                            errorDialogState = true
                        }
                    }
                }
            },
            modifier = Modifier.padding(24.dp)
        )
        {
            Text("Login & Sign Up")
        }
        SignUpDialog(navController = navController, loginViewModel = model)
    }
}

