package com.example.travelagency.model.di.repository

import com.example.travelagency.model.Destination
import com.example.travelagency.model.TeamMember
import com.example.travelagency.model.local.entity.BookingEntity
import com.example.travelagency.model.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface TravelRepository {

    // ── Destinations ─────────────────────────────────────────────────────────
    fun getAllDestinations(): Flow<List<Destination>>
    suspend fun getDestinationById(id: Long): Destination?
    suspend fun insertDestinationWithIncludes(destination: Destination)
    suspend fun updateDestination(destination: Destination)
    suspend fun deleteDestination(destination: Destination)
    suspend fun seedDestinationsIfEmpty()

    // ── Bookings ─────────────────────────────────────────────────────────────
    fun getAllBookings(): Flow<List<BookingEntity>>
    suspend fun insertBooking(booking: BookingEntity): Long
    suspend fun deleteBooking(booking: BookingEntity)

    // ── User Profile ─────────────────────────────────────────────────────────
    fun getUserProfile(): Flow<UserProfileEntity?>
    suspend fun saveUserProfile(profile: UserProfileEntity)
    suspend fun deleteUserProfile()

    // ── Team Members ─────────────────────────────────────────────────────────
    fun getAllTeamMembers(): Flow<List<TeamMember>>
    suspend fun seedTeamMembersIfEmpty()
}