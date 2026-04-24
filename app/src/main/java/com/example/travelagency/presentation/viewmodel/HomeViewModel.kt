package com.example.travelagency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.popularCities
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.ui.screens.home.util.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // ── Derived state 1: top rated destinations (rating >= 4.8) ──────────────
    val topRatedDestinations = _uiState.map { state ->
        state.featuredDestinations.filter { it.rating >= 4.8f }
    }

    // ── Derived state 2: has featured content to show ────────────────────────
    val hasFeaturedContent: Boolean
        get() = _uiState.value.featuredDestinations.isNotEmpty()

    // ── Derived state 3: search is active ────────────────────────────────────
    val isSearchActive: Boolean
        get() = _uiState.value.searchQuery.isNotBlank()

    init {
        _uiState.value = HomeUiState(
            featuredDestinations = sampleDestinations.take(4),
            popularCities = popularCities
        )
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}