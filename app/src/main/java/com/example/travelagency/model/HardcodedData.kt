package com.example.travelagency.model

val sampleDestinations = listOf(
    Destination(
        id = 1, name = "Paris", country = "France",
        description = "The city of lights and love. Famous for the Eiffel Tower, world-class cuisine, and romantic atmosphere.",
        price = 1200, duration = 7, category = "Romance", rating = 4.9f,
        includes = listOf("Flight", "Hotel", "Breakfast", "City Tour", "River Cruise")
    ),
    Destination(
        id = 2, name = "Santorini", country = "Greece",
        description = "Beautiful island with white-washed buildings, stunning sunsets, and volcanic beaches.",
        price = 1500, duration = 5, category = "Romance", rating = 4.8f,
        includes = listOf("Flight", "Cave Hotel", "Breakfast", "Sailing Trip", "Wine Tasting")
    ),
    Destination(
        id = 3, name = "Tokyo", country = "Japan",
        description = "A blend of ultra-modern and traditional, with amazing food, culture, and technology.",
        price = 1800, duration = 10, category = "Culture", rating = 4.8f,
        includes = listOf("Flight", "Hotel", "Rail Pass", "City Tour", "Tea Ceremony")
    ),
    Destination(
        id = 4, name = "New York", country = "USA",
        description = "The city that never sleeps. Visit Times Square, Central Park, Broadway and iconic skyline views.",
        price = 1100, duration = 6, category = "City", rating = 4.5f,
        includes = listOf("Flight", "Hotel", "Broadway Ticket", "City Pass", "Statue of Liberty")
    ),
    Destination(
        id = 5, name = "Bali", country = "Indonesia",
        description = "Tropical paradise with temples, rice fields, beautiful beaches, and rich spiritual culture.",
        price = 900, duration = 8, category = "Adventure", rating = 4.7f,
        includes = listOf("Flight", "Villa", "Breakfast", "Temple Tour", "Surfing Lesson")
    ),
    Destination(
        id = 6, name = "Rome", country = "Italy",
        description = "The eternal city full of ancient history, breathtaking art, and incredible food.",
        price = 1300, duration = 7, category = "Culture", rating = 4.6f,
        includes = listOf("Flight", "Hotel", "Colosseum Tour", "Vatican Visit", "Food Tour")
    ),
    Destination(
        id = 7, name = "Dubai", country = "UAE",
        description = "Ultra-modern city with record-breaking skyscrapers, luxury shopping, and desert adventures.",
        price = 1600, duration = 6, category = "Luxury", rating = 4.6f,
        includes = listOf("Flight", "5-Star Hotel", "Desert Safari", "Burj Khalifa", "City Tour")
    ),
    Destination(
        id = 8, name = "Machu Picchu", country = "Peru",
        description = "Ancient Incan citadel set high in the Andes Mountains, one of the world's greatest wonders.",
        price = 2100, duration = 9, category = "Adventure", rating = 4.9f,
        includes = listOf("Flight", "Hotel", "Guided Trek", "Train Pass", "Entrance Fees")
    )
)

val destinationCategories = listOf("All", "Adventure", "Romance", "Culture", "Luxury", "City")

val popularCities = listOf("Paris", "Tokyo", "Bali", "New York", "Dubai", "Rome")

val teamMembers = listOf(
    TeamMember(1, "Amina Hadžić", "CEO & Founder", "20+ years crafting unforgettable journeys across 5 continents."),
    TeamMember(2, "Damir Kovač", "Head of Operations", "Expert in logistics ensuring every trip runs smoothly."),
    TeamMember(3, "Lejla Mujić", "Travel Specialist", "Passionate about sustainable and off-the-beaten-path travel.")
)