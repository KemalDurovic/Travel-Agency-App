package com.example.travelagency.model

data class Destination(
        val id: Int,
        val name: String,
        val country: String,
        val description: String,
        val price: Int,
        val duration: Int,
        val imageUrl: String = "",
        val category: String = "Adventure",
        val rating: Float = 4.5f,
        val includes: List<String> = emptyList()
)

data class BookingForm(
        val fullName: String = "",
        val email: String = "",
        val travelers: Int = 1,
        val destinationId: Int = -1
)

data class TeamMember(
        val id: Int,
        val name: String,
        val role: String,
        val bio: String
)