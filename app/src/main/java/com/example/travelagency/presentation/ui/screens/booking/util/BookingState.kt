package com.example.travelagency.presentation.ui.screens.booking.util

import com.example.travelagency.model.BookingForm

data class BookingUiState(
    val form: BookingForm = BookingForm(),
    val nameError: String? = null,
    val emailError: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false
)