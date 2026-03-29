package com.example.travelagency.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.Destination
import com.example.travelagency.model.sampleDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Destination>>(emptyList())
    val searchResults: StateFlow<List<Destination>> = _searchResults.asStateFlow()

    fun onQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = sampleDestinations.filter { destination ->
                destination.name.contains(query, ignoreCase = true) ||
                        destination.country.contains(query, ignoreCase = true)
            }
        }
    }
}