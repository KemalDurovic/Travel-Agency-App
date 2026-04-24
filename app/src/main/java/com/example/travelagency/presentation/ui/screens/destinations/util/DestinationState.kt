package com.example.travelagency.presentation.ui.screens.destinations.util

import com.example.travelagency.model.Destination

data class DestinationsUiState(
    val allDestinations: List<Destination> = emptyList(),
    val filteredDestinations: List<Destination> = emptyList(),
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

data class DestinationDetailUiState(
    val destination: Destination? = null,
    val travelers: Int = 1,
    val isBooked: Boolean = false,
    val showBookingDialog: Boolean = false
)