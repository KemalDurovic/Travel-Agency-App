package com.example.travelagency.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A73E8),
    secondary = Color(0xFF03A9F4),
    tertiary = Color(0xFF4CAF50)
)

@Composable
fun TravelAgencyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = _root_ide_package_.com.example.travelagency.presentation.ui.theme.LightColorScheme,
        content = content
    )
}