package com.example.travelagency.model

data class Destination(
        val id: Int,
        val name: String,
        val country: String,
        val description: String,
        val price: Int,
        val duration: Int, // in days
        val imageUrl: String = ""
)

data class BookingForm(
        val fullName: String = "",
        val email: String = "",
        val travelers: Int = 1,
        val destinationId: Int = -1
)

val sampleDestinations = listOf(
        Destination(1, "Paris", "France", "The city of lights and love. Famous for the Eiffel Tower and cuisine.", 1200, 7),
        Destination(2, "Santorini", "Greece", "Beautiful island with white-washed buildings and stunning sunsets.", 1500, 5),
        Destination(3, "Tokyo", "Japan", "A blend of ultra-modern and traditional, with amazing food and culture.", 1800, 10),
        Destination(4, "New York", "USA", "The city that never sleeps. Visit Times Square, Central Park and more.", 1100, 6),
        Destination(5, "Bali", "Indonesia", "Tropical paradise with temples, rice fields, and beautiful beaches.", 900, 8),
        Destination(6, "Rome", "Italy", "The eternal city full of ancient history, art, and incredible food.", 1300, 7)
)