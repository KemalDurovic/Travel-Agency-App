package com.example.travelagency.ui.screens.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search destinations or countries...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        singleLine = true
    )
}

@Composable
fun EmptySearchState(query: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (query.isBlank()) {
                Text(text = "🔍", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Search for destinations", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Type a city or country name",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            } else {
                Text(text = "😕", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("No results for \"$query\"", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Try a different search term",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}