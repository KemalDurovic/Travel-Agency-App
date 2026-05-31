package com.example.travelagency.model.network.util

import com.example.travelagency.model.network.dto.DestinationDto

data class NetworkUiState(
    val isLoading: Boolean = false,
    val destinations: List<DestinationDto> = emptyList(),
    val selectedDestination: DestinationDto? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)