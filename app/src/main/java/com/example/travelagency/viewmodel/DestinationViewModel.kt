package com.example.travelagency.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.Destination
import com.example.travelagency.model.sampleDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DestinationViewModel : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(sampleDestinations)
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    private val _selectedDestination = MutableStateFlow<Destination?>(null)
    val selectedDestination: StateFlow<Destination?> = _selectedDestination.asStateFlow()

    fun selectDestination(id: Int) {
        _selectedDestination.value = sampleDestinations.find { it.id == id }
    }
}