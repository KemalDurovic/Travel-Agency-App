package com.example.travelagency.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelagency.model.local.dao.BookingDao
import com.example.travelagency.model.local.dao.DestinationDao
import com.example.travelagency.model.local.dao.TeamMemberDao
import com.example.travelagency.model.local.dao.UserProfileDao
import com.example.travelagency.model.local.entity.BookingEntity
import com.example.travelagency.model.local.entity.DestinationEntity
import com.example.travelagency.model.local.entity.DestinationIncludeEntity
import com.example.travelagency.model.local.entity.TeamMemberEntity
import com.example.travelagency.model.local.entity.UserProfileEntity

@Database(
    entities = [
        DestinationEntity::class,
        DestinationIncludeEntity::class,
        BookingEntity::class,
        UserProfileEntity::class,
        TeamMemberEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TravelDatabase : RoomDatabase() {
    abstract fun destinationDao(): DestinationDao
    abstract fun bookingDao(): BookingDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun teamMemberDao(): TeamMemberDao
}