package com.example.newscenter.ui.page

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
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
import com.example.newscenter.R
import com.example.newscenter.ui.model.AppViewModel


@Composable
fun UserPage(model: AppViewModel) {
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
            Text(
                text = if (currentUser!!.phone != "") currentUser!!.phone else "No Phone Number",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = if (currentUser!!.email != "") currentUser!!.email else "No Email Address",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier =Modifier.padding(24.dp))
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {

            }
        }
    }

}