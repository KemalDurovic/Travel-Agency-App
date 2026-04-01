package com.example.travelagency.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagency.ui.screens.destinations.components.DestinationListItem
import com.example.travelagency.ui.screens.search.components.EmptySearchState
import com.example.travelagency.ui.screens.search.components.SearchBar
import com.example.travelagency.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onDestinationClick: (Int) -> Unit
) {
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val results by viewModel.searchResults.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        SearchBar(query = query, onQueryChange = { viewModel.onQueryChange(it) })

        Spacer(modifier = Modifier.height(8.dp))

        if (results.isEmpty()) {
            EmptySearchState(query = query)
        } else {
            Text(
                text = "${results.size} result(s) found",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            LazyColumn {
                items(results) { destination ->
                    DestinationListItem(
                        destination = destination,
                        onClick = { onDestinationClick(destination.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}