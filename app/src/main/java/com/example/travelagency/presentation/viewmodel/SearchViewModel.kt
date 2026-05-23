package com.example.travelagency.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagency.TravelApplication
import com.example.travelagency.model.Destination
import com.example.travelagency.presentation.ui.screens.search.util.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as TravelApplication).container.repository

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var allDestinations: List<Destination> = emptyList()

    // ── Derived state: no results found after searching ──────────────────────
    val showEmptyState: Boolean
        get() = _uiState.value.hasSearched && _uiState.value.results.isEmpty()

    init {
        loadDestinations()
    }

    private fun loadDestinations() {
        viewModelScope.launch {
            repository.getAllDestinations().collect { destinations ->
                allDestinations = destinations
            }
        }
    }

    fun onQueryChange(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState()
            return
        }
        val results = allDestinations.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.country.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
        _uiState.value = SearchUiState(query = query, results = results, hasSearched = true)
    }
}