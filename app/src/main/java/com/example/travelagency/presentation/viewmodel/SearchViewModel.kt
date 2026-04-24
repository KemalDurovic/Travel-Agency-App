package com.example.travelagency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.ui.screens.search.util.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // ── Derived state: no results found after searching ──────────────────────
    val showEmptyState: Boolean
        get() = _uiState.value.hasSearched && _uiState.value.results.isEmpty()

    fun onQueryChange(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState()
            return
        }
        val results = sampleDestinations.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.country.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
        _uiState.value = SearchUiState(query = query, results = results, hasSearched = true)
    }
}