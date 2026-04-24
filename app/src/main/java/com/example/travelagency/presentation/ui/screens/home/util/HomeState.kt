package com.example.travelagency.presentation.ui.screens.home.util

import com.example.travelagency.model.Destination

data class HomeUiState(
    val featuredDestinations: List<Destination> = emptyList(),
    val popularCities: List<String> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)