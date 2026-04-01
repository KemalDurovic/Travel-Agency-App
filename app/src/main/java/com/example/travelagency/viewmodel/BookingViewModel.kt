package com.example.travelagency.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.model.BookingForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookingViewModel : ViewModel() {

    private val _form = MutableStateFlow(BookingForm())
    val form: StateFlow<BookingForm> = _form.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _bookingSuccess = MutableStateFlow(false)
    val bookingSuccess: StateFlow<Boolean> = _bookingSuccess.asStateFlow()

    fun onNameChange(name: String) {
        _form.value = _form.value.copy(fullName = name)
        _nameError.value = if (name.isBlank()) "Name cannot be empty" else null
    }

    fun onEmailChange(email: String) {
        _form.value = _form.value.copy(email = email)
        _emailError.value = when {
            email.isBlank() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email address"
            else -> null
        }
    }

    fun onTravelersChange(count: Int) {
        _form.value = _form.value.copy(travelers = count)
    }

    fun setDestination(destinationId: Int) {
        _form.value = _form.value.copy(destinationId = destinationId)
    }

    val isFormValid: Boolean
        get() {
            val f = _form.value
            return f.fullName.isNotBlank() &&
                    f.email.isNotBlank() &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(f.email).matches() &&
                    f.travelers >= 1 &&
                    f.destinationId != -1
        }

    fun submitBooking() {
        if (isFormValid) {
            _bookingSuccess.value = true
        }
    }

    fun resetBooking() {
        _form.value = BookingForm()
        _nameError.value = null
        _emailError.value = null
        _bookingSuccess.value = false
    }
}