package com.example.travelagency.model.di.repository

import com.example.travelagency.model.Destination
import com.example.travelagency.model.TeamMember
import com.example.travelagency.model.local.dao.BookingDao
import com.example.travelagency.model.local.dao.DestinationDao
import com.example.travelagency.model.local.dao.TeamMemberDao
import com.example.travelagency.model.local.dao.UserProfileDao
import com.example.travelagency.model.local.entity.BookingEntity
import com.example.travelagency.model.local.entity.UserProfileEntity
import com.example.travelagency.model.di.repository.mappers.toDomain
import com.example.travelagency.model.di.repository.mappers.toEntity
import com.example.travelagency.model.di.repository.mappers.toIncludeEntities
import com.example.travelagency.model.sampleDestinations
import com.example.travelagency.model.teamMembers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val destinationDao: DestinationDao,
    private val bookingDao: BookingDao,
    private val userProfileDao: UserProfileDao,
    private val teamMemberDao: TeamMemberDao
) : TravelRepository {

    // ── Destinations ─────────────────────────────────────────────────────────

    override fun getAllDestinations(): Flow<List<Destination>> =
        destinationDao.getAllDestinationsWithIncludes().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getDestinationById(id: Long): Destination? =
        destinationDao.getDestinationWithIncludes(id)?.toDomain()

    override suspend fun insertDestinationWithIncludes(destination: Destination) {
        val entity = destination.toEntity()
        val insertedId = destinationDao.insertDestination(entity)
        val includes = destination.toIncludeEntities(insertedId)
        destinationDao.insertIncludes(includes)
    }

    override suspend fun updateDestination(destination: Destination) {
        destinationDao.updateDestination(destination.toEntity())
    }

    override suspend fun deleteDestination(destination: Destination) {
        destinationDao.deleteDestination(destination.toEntity())
    }

    // Seeds the DB with hardcoded data only on first launch (empty DB)
    override suspend fun seedDestinationsIfEmpty() {
        if (destinationDao.getDestinationCount() == 0) {
            sampleDestinations.forEach { destination ->
                insertDestinationWithIncludes(destination)
            }
        }
    }

    // ── Bookings ─────────────────────────────────────────────────────────────

    override fun getAllBookings(): Flow<List<BookingEntity>> =
        bookingDao.getAllBookings()

    override suspend fun insertBooking(booking: BookingEntity): Long =
        bookingDao.insertBooking(booking)

    override suspend fun deleteBooking(booking: BookingEntity) =
        bookingDao.deleteBooking(booking)

    // ── User Profile ─────────────────────────────────────────────────────────

    override fun getUserProfile(): Flow<UserProfileEntity?> =
        userProfileDao.getUserProfile()

    override suspend fun saveUserProfile(profile: UserProfileEntity) =
        userProfileDao.insertOrUpdateProfile(profile)

    override suspend fun deleteUserProfile() =
        userProfileDao.deleteProfile()

    // ── Team Members ─────────────────────────────────────────────────────────

    override fun getAllTeamMembers(): Flow<List<TeamMember>> =
        teamMemberDao.getAllTeamMembers().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun seedTeamMembersIfEmpty() {
        if (teamMemberDao.getTeamMemberCount() == 0) {
            teamMembers.forEach { member ->
                teamMemberDao.insertTeamMember(member.toEntity())
            }
        }
    }
}