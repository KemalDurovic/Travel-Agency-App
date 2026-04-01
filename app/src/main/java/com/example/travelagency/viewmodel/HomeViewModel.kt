package com.example.travelagency.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.Destination
import com.example.travelagency.model.sampleDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(sampleDestinations)
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    private val _featuredDestinations = MutableStateFlow<List<Destination>>(
        sampleDestinations.take(3)
    )
    val featuredDestinations: StateFlow<List<Destination>> = _featuredDestinations.asStateFlow()
}