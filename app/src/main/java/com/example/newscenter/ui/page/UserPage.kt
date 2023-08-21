package com.example.newscenter.ui.page


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newscenter.R
import com.example.newscenter.db.App
import com.example.newscenter.ui.model.AppViewModel
import com.example.newscenter.ui.view.EditableText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun UserPage(navController: NavHostController, model: AppViewModel) {
    val currentUser by model.currentUser.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        val avatar = painterResource(id = R.drawable.no_gravity_3)
        Image(
            painter = avatar,
            contentDescription = "UserPage",
            modifier = Modifier.clip(shape = androidx.compose.foundation.shape.CircleShape)
        )
        Text(
            text = currentUser!!.username,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Phone",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            EditableText(
                default = currentUser!!.phone,
                description = "Email Address",
                placeholder = "No Phone Number",
                pattern = "^[1][3,4,5,7,8][0-9]{9}$",
                onConfirm = {
                    currentUser!!.phone = it
                    model.onUserChange(currentUser!!)
                }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            EditableText(
                default = currentUser!!.email,
                description = "Email Address",
                placeholder = "No Email Address",
                pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$",
                onConfirm = {
                    currentUser!!.email = it
                    model.onUserChange(currentUser!!)
                    CoroutineScope(Dispatchers.IO).launch {
                        App.db.userDao().update(currentUser!!)
                    }
                })
        }
        Spacer(modifier = Modifier.padding(24.dp))
        Button(onClick = {
            navController.navigate("login_page")
        }) {
            Text("Log out", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}