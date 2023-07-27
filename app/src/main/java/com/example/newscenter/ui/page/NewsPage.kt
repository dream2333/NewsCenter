package com.example.newscenter.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.newscenter.ui.model.AppViewModel

@Composable
fun NewsPage(model: AppViewModel) {
    val newsItem by model.newsContent.collectAsState()
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())

    ) {
        if (newsItem != null) {
            Text(
                text = newsItem!!.title,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = newsItem!!.source,
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                letterSpacing = 1.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = newsItem!!.time,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = newsItem!!.category!!,
                    fontSize = 13.sp,
                    color = Color(0xFF03A9F4),
                    textAlign = TextAlign.Start,
                    letterSpacing = 1.sp,
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = newsItem!!.content!!,
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 2.sp,
            )
            Spacer(modifier = Modifier.padding(36.dp))
        }

    }

}