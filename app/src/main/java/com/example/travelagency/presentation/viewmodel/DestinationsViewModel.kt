package com.example.travelagency.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagency.TravelApplication
import com.example.travelagency.model.Destination
import com.example.travelagency.presentation.ui.screens.destinations.util.DestinationsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DestinationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as TravelApplication).container.repository

    private val _uiState = MutableStateFlow(DestinationsUiState())
    val uiState: StateFlow<DestinationsUiState> = _uiState.asStateFlow()

    // ── Derived state 1: result count label ──────────────────────────────────
    val resultCountLabel: String
        get() {
            val count = _uiState.value.filteredDestinations.size
            return if (count == 0) "No results" else "$count destination(s) found"
        }

    // ── Derived state 2: empty list check ────────────────────────────────────
    val isEmpty: Boolean
        get() = _uiState.value.filteredDestinations.isEmpty()

    // ── Derived state 3: is any filter currently active ──────────────────────
    val isFilterActive: Boolean
        get() = _uiState.value.selectedCategory != "All" || _uiState.value.searchQuery.isNotBlank()

    init {
        loadDestinations()
    }

    private fun loadDestinations() {
        viewModelScope.launch {
            repository.getAllDestinations().collect { destinations ->
                _uiState.value = _uiState.value.copy(
                    allDestinations = destinations,
                    filteredDestinations = destinations
                )
            }
        }
    }

    fun onCategorySelected(category: String) {
        val filtered = applyFilters(category, _uiState.value.searchQuery)
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            filteredDestinations = filtered
        )
    }

    fun onSearchQueryChange(query: String) {
        val filtered = applyFilters(_uiState.value.selectedCategory, query)
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredDestinations = filtered
        )
    }

    private fun applyFilters(category: String, query: String): List<Destination> {
        return _uiState.value.allDestinations.filter { dest ->
            val matchesCategory = category == "All" || dest.category == category
            val matchesQuery = query.isBlank() ||
                    dest.name.contains(query, ignoreCase = true) ||
                    dest.country.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
}