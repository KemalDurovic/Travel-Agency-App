package com.example.travelagency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.BookingForm
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.presentation.ui.screens.booking.util.BookingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    // ── Derived state 1: total price ─────────────────────────────────────────
    val totalPrice: Int
        get() {
            val dest = sampleDestinations.find { it.id == _uiState.value.form.destinationId }
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
        get() = sampleDestinations.find { it.id == _uiState.value.form.destinationId }?.name ?: ""

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
            _uiState.value = _uiState.value.copy(isSuccess = true)
        }
    }

    fun resetBooking() {
        _uiState.value = BookingUiState()
    }
}