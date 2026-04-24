package com.example.travelagency.presentation.ui.screens.search.util

import com.example.travelagency.model.Destination

data class SearchUiState(
    val query: String = "",
    val results: List<Destination> = emptyList(),
    val hasSearched: Boolean = false,
    val isLoading: Boolean = false
)