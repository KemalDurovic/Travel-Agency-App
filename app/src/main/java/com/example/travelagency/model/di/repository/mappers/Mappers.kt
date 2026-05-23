package com.example.travelagency.model.di.repository.mappers

import com.example.travelagency.model.BookingForm
import com.example.travelagency.model.Destination
import com.example.travelagency.model.TeamMember
import com.example.travelagency.model.local.entity.BookingEntity
import com.example.travelagency.model.local.entity.DestinationEntity
import com.example.travelagency.model.local.entity.DestinationIncludeEntity
import com.example.travelagency.model.local.entity.TeamMemberEntity
import com.example.travelagency.model.local.entity.UserProfileEntity
import com.example.travelagency.model.local.dao.DestinationWithIncludes
import com.example.travelagency.presentation.ui.screens.profile.util.ProfileUiState

// ── Destination ──────────────────────────────────────────────────────────────

fun DestinationWithIncludes.toDomain(): Destination = Destination(
    id = destination.id.toInt(),
    name = destination.name,
    country = destination.country,
    description = destination.description,
    price = destination.price,
    duration = destination.duration,
    imageUrl = destination.imageUrl,
    category = destination.category,
    rating = destination.rating,
    includes = includes.map { it.includeItem }
)

fun Destination.toEntity(): DestinationEntity = DestinationEntity(
    id = id.toLong(),
    name = name,
    country = country,
    description = description,
    price = price,
    duration = duration,
    imageUrl = imageUrl,
    category = category,
    rating = rating
)

fun Destination.toIncludeEntities(destinationId: Long): List<DestinationIncludeEntity> =
    includes.map { item ->
        DestinationIncludeEntity(
            destinationId = destinationId,
            includeItem = item
        )
    }

// ── Booking ──────────────────────────────────────────────────────────────────

fun BookingForm.toEntity(totalPrice: Int): BookingEntity = BookingEntity(
    fullName = fullName,
    email = email,
    travelers = travelers,
    destinationId = destinationId.toLong(),
    totalPrice = totalPrice
)

// ── TeamMember ───────────────────────────────────────────────────────────────

fun TeamMemberEntity.toDomain(): TeamMember = TeamMember(
    id = id.toInt(),
    name = name,
    role = role,
    bio = bio
)

fun TeamMember.toEntity(): TeamMemberEntity = TeamMemberEntity(
    id = id.toLong(),
    name = name,
    role = role,
    bio = bio
)

// ── UserProfile ───────────────────────────────────────────────────────────────

fun UserProfileEntity.toUiState(): ProfileUiState = ProfileUiState(
    firstName = firstName,
    lastName = lastName,
    email = email,
    phone = phone,
    country = country
)

fun ProfileUiState.toEntity(): UserProfileEntity = UserProfileEntity(
    id = 1,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phone = phone,
    country = country
)