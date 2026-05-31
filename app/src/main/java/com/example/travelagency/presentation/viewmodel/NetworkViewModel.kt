package com.example.travelagency.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagency.TravelApplication
import com.example.travelagency.model.network.dto.DestinationDto
import com.example.travelagency.model.network.util.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as TravelApplication).container.networkRepository

    private val _uiState = MutableStateFlow(NetworkUiState())
    val uiState: StateFlow<NetworkUiState> = _uiState.asStateFlow()

    // ── Derived state: has destinations loaded ───────────────────────────────
    val hasDestinations: Boolean
        get() = _uiState.value.destinations.isNotEmpty()

    // ── Derived state: is in error state ────────────────────────────────────
    val hasError: Boolean
        get() = _uiState.value.error != null

    init {
        loadDestinations()
    }

    fun loadDestinations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getDestinations()
                .onSuccess { destinations ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        destinations = destinations
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun createDestination(destination: DestinationDto) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.createDestination(destination)
                .onSuccess {
                    loadDestinations()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to create destination"
                    )
                }
        }
    }

    fun updateDestination(id: String, destination: DestinationDto) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.updateDestination(id, destination)
                .onSuccess {
                    loadDestinations()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to update destination"
                    )
                }
        }
    }

    fun deleteDestination(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.deleteDestination(id)
                .onSuccess {
                    loadDestinations()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to delete destination"
                    )
                }
        }
    }

    fun selectDestination(destination: DestinationDto) {
        _uiState.value = _uiState.value.copy(selectedDestination = destination)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}