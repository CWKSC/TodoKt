package com.example.todokt.ui.mainpage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoCard(
    date: String, time: String, description: String, onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onLongClick = onLongClick, onClick = {}),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 6.dp,
                    bottom = 16.dp,
                    start = 8.dp,
                    end = 16.dp
                )
            ) {
                Text(text = date, style = MaterialTheme.typography.titleLarge)
                Text(text = time, style = MaterialTheme.typography.titleMedium)
            }
            Divider(
                color = Color.Red,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(text = description, fontSize = 18.sp, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
