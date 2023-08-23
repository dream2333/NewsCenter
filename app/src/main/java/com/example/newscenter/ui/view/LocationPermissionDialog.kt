import android.Manifest
import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.newscenter.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionDialog(onAllSuccess: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    var showDialog by remember {
        mutableStateOf(true)
    }
    if (locationPermissionsState.allPermissionsGranted) {
        editor.putBoolean("allLocationPermissionDone", true).apply()
        onAllSuccess()
    } else if (showDialog && !sharedPreferences.getBoolean("allLocationPermissionDone", false)) {
        val allPermissionsRevoked =
            locationPermissionsState.permissions.size ==
                    locationPermissionsState.revokedPermissions.size
        val textToShow = if (!allPermissionsRevoked) {
            stringResource(id = R.string.partly_location_success)
        } else if (locationPermissionsState.shouldShowRationale) {
            // 所有权限被拒绝
            stringResource(id = R.string.first_get_location_permission)
        } else {
            // 第一次询问权限
            stringResource(id = R.string.first_get_location_permission)
        }

        val buttonText = if (!allPermissionsRevoked) {
            "Allow precise location"
        } else {
            "OK"
        }

        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Permission") },
            //显示有关对话框目的的详细信息的文本。提供的文本样式默认为 Typography.body1
            text = { Text(text = textToShow) },
            confirmButton = {
                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text(buttonText)
                }
            },
            //用于关闭对话框的按钮。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置,null则不显示
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("No")
                }
            },
            properties = DialogProperties(
                //是否可以通过按下后退按钮来关闭对话框。 如果为 true，按下后退按钮将调用 onDismissRequest。
                dismissOnBackPress = false,
                //是否可以通过在对话框边界外单击来关闭对话框。 如果为 true，单击对话框外将调用 onDismissRequest
                dismissOnClickOutside = false,
                //用于在对话框窗口上设置 WindowManager.LayoutParams.FLAG_SECURE 的策略。
                securePolicy = SecureFlagPolicy.Inherit,
                //对话框内容的宽度是否应限制为平台默认值，小于屏幕宽度。
                usePlatformDefaultWidth = true
            )
        )

    }
}
