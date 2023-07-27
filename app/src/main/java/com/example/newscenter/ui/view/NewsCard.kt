package com.example.newscenter.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun NewsCard(imgUrl: String, title: String, source: String, category: String, onClick: ()->Unit) {
    var downloading: Boolean by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable(onClick=onClick)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(120.dp, 90.dp)
                .align(Alignment.CenterVertically),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .placeholder(
                        visible = downloading,
                        highlight = PlaceholderHighlight.shimmer(),
                    )
                    .clip(shape = RoundedCornerShape(6)),
                onSuccess = {
                    downloading = false
                },
                onError = {
                    downloading = false
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = source,
                    modifier = Modifier
                        .padding(8.dp),
                    color = androidx.compose.ui.graphics.Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = category,
                    modifier = Modifier
                        .padding(8.dp),
                    color = Color(0xFF03A9F4),
                    fontSize = 12.sp
                )
            }

        }

    }
}

