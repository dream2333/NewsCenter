import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newscenter.db.UserDao
import com.example.newscenter.ui.model.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginView(navController: NavHostController, model: LoginViewModel, userDao: UserDao) {
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
                //使用协程操作数据库
                CoroutineScope(Dispatchers.IO).launch {
                    val users = userDao.getUser(username)
                    if (users.isEmpty()) {
                        model.changeDialogState()
                    } else if (users[0].password == password) {
                        Log.i("登录",users[0].toString())
                        //此处需要切换回主线程
                        withContext(Dispatchers.Main) {
                            navController.navigate("home_page")
                        }
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
