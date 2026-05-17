package com.example.travelagency.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagency.TravelApplication
import com.example.travelagency.model.Destination
import com.example.travelagency.model.di.repository.mappers.toEntity
import com.example.travelagency.presentation.ui.screens.booking.util.BookingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as TravelApplication).container.repository

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    private var allDestinations: List<Destination> = emptyList()

    // ── Derived state 1: total price ─────────────────────────────────────────
    val totalPrice: Int
        get() {
            val dest = allDestinations.find { it.id == _uiState.value.form.destinationId }
            return (dest?.price ?: 0) * _uiState.value.form.travelers
        }

    // ── Derived state 2: form is valid ───────────────────────────────────────
    val isFormValid: Boolean
        get() {
            val form = _uiState.value.form
            return form.fullName.isNotBlank() &&
                    form.email.contains("@") && form.email.contains(".") &&
                    form.travelers >= 1 &&
                    form.destinationId != -1
        }

    // ── Derived state 3: selected destination name ───────────────────────────
    val selectedDestinationName: String
        get() = allDestinations.find { it.id == _uiState.value.form.destinationId }?.name ?: ""

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

    fun setDestination(id: Int) {
        _uiState.value = _uiState.value.copy(form = _uiState.value.form.copy(destinationId = id))
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            form = _uiState.value.form.copy(fullName = name),
            nameError = if (name.isBlank()) "Name is required" else null
        )
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            form = _uiState.value.form.copy(email = email),
            emailError = when {
                email.isBlank() -> "Email is required"
                !email.contains("@") -> "Enter a valid email"
                else -> null
            }
        )
    }

    fun onTravelersChange(count: Int) {
        if (count in 1..10) {
            _uiState.value = _uiState.value.copy(
                form = _uiState.value.form.copy(travelers = count)
            )
        }
    }

    fun submitBooking() {
        if (isFormValid) {
            viewModelScope.launch {
                val booking = _uiState.value.form.toEntity(totalPrice)
                repository.insertBooking(booking)
                _uiState.value = _uiState.value.copy(isSuccess = true)
            }
        }
    }

    fun resetBooking() {
        _uiState.value = BookingUiState()
    }
}