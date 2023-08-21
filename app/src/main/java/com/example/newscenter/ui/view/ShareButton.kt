package com.example.newscenter.ui.view

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex

@Composable
fun ShareButton(url:String){
    val context = LocalContext.current
    FloatingActionButton(modifier = Modifier.zIndex(10000f), onClick = {
        val intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.setType("text/plain")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.applicationContext.startActivity(intent)
    }) {
        Icon(
            imageVector = Icons.Filled.Share, contentDescription = "share"
        )
    }
}