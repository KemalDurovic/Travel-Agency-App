package com.example.travelagency.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagency.TravelApplication
import com.example.travelagency.model.popularCities
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.ui.screens.home.util.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as TravelApplication).container.repository

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
        loadDestinations()
    }

    private fun loadDestinations() {
        viewModelScope.launch {
            repository.seedDestinationsIfEmpty()
            repository.getAllDestinations().collect { destinations ->
                _uiState.value = HomeUiState(
                    featuredDestinations = destinations.take(4),
                    popularCities = popularCities
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}