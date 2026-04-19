package com.example.travelagency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelagency.presentation.ui.screens.profile.util.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ── Derived state: full name ─────────────────────────────────────────────
    val fullName: String
        get() = "${_uiState.value.firstName} ${_uiState.value.lastName}"

    // ── Derived state: initials for avatar ───────────────────────────────────
    val initials: String
        get() = "${_uiState.value.firstName.take(1)}${_uiState.value.lastName.take(1)}"

    fun onFirstNameChange(v: String) = update { copy(firstName = v, firstNameError = null, isSaved = false) }
    fun onLastNameChange(v: String) = update { copy(lastName = v, lastNameError = null, isSaved = false) }
    fun onEmailChange(v: String) = update { copy(email = v, emailError = null, isSaved = false) }
    fun onPhoneChange(v: String) = update { copy(phone = v, isSaved = false) }
    fun onCountryChange(v: String) = update { copy(country = v, isSaved = false) }
    fun onEditToggle() = update { copy(isEditing = !isEditing) }

    fun onSave() {
        val s = _uiState.value
        val firstNameError = if (s.firstName.isBlank()) "Required" else null
        val lastNameError = if (s.lastName.isBlank()) "Required" else null
        val emailError = when {
            s.email.isBlank() -> "Required"
            !s.email.contains("@") -> "Invalid email"
            else -> null
        }
        if (firstNameError != null || lastNameError != null || emailError != null) {
            update { copy(firstNameError = firstNameError, lastNameError = lastNameError, emailError = emailError) }
            return
        }
        update { copy(isEditing = false, isSaved = true) }
    }

    private fun update(block: ProfileUiState.() -> ProfileUiState) {
        _uiState.value = _uiState.value.block()
    }
}