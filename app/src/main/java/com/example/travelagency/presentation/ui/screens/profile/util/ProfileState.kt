package com.example.travelagency.presentation.ui.screens.profile.util

data class ProfileUiState(
    val firstName: String = "Elma",
    val lastName: String = "Softić",
    val email: String = "elma@example.com",
    val phone: String = "+387 61 123 456",
    val country: String = "Bosnia and Herzegovina",
    val isEditing: Boolean = false,
    val isSaved: Boolean = false,
    val savedTripsCount: Int = 2,
    val completedTripsCount: Int = 5,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null
)