package com.example.travelagency.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyState(
    emoji: String = "🔍",
    title: String = "No items available",
    subtitle: String = "Try a different search or filter",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = emoji, fontSize = 48.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
    }
}

fun categoryEmoji(category: String): String = when (category) {
    "Romance" -> "💑"
    "Adventure" -> "🏔️"
    "Culture" -> "🏯"
    "Luxury" -> "💎"
    "City" -> "🏙️"
    else -> "✈️"
}

@Composable
fun CategoryBadge(category: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE3F2FD))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = category, color = Color(0xFF1565C0), fontSize = 11.sp)
    }
}