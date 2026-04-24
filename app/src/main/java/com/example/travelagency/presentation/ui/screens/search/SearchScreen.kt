package com.example.travelagency.presentation.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.model.destinationCategories
import com.example.travelagency.presentation.ui.components.CategoryChip
import com.example.travelagency.presentation.ui.components.DestinationCard
import com.example.travelagency.presentation.ui.components.EmptyState
import com.example.travelagency.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onDestinationClick: (Int, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Search",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFF0D47A1),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        // Search input
        OutlinedTextField(
            value = uiState.query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search by name, country, category...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF1565C0))
            },
            shape = RoundedCornerShape(50.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1565C0),
                unfocusedBorderColor = Color(0xFFBBDEFB)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category quick-filter — LazyRow #4
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(destinationCategories) { category ->
                CategoryChip(
                    label = category,
                    isSelected = false,
                    onSelect = { viewModel.onQueryChange(if (category == "All") "" else category) }
                )
            }
        }

        // Results
        when {
            // Not searched yet
            !uiState.hasSearched -> {
                EmptyState(
                    emoji = "✈️",
                    title = "Where do you want to go?",
                    subtitle = "Type a destination, country, or category",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Searched but no results — empty state
            uiState.results.isEmpty() -> {
                EmptyState(
                    emoji = "😔",
                    title = "No results for \"${uiState.query}\"",
                    subtitle = "Try a different search term",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Results found — LazyColumn #4 (satisfies 2x LazyColumn requirement)
            else -> {
                Text(
                    text = "${uiState.results.size} result(s) found",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.results, key = { it.id }) { dest ->
                        DestinationCard(
                            destination = dest,
                            onClick = { onDestinationClick(dest.id, dest.name) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}