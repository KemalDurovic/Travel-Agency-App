package com.example.travelagency.model.di

import android.content.Context
import androidx.room.Room
import com.example.travelagency.model.di.repository.TravelRepository
import com.example.travelagency.model.di.repository.TravelRepositoryImpl
import com.example.travelagency.model.local.db.TravelDatabase

class AppContainer(context: Context) {

    private val database: TravelDatabase = Room.databaseBuilder(
        context.applicationContext,
        TravelDatabase::class.java,
        "travel_database"
    ).build()

    val repository: TravelRepository = TravelRepositoryImpl(
        destinationDao = database.destinationDao(),
        bookingDao = database.bookingDao(),
        userProfileDao = database.userProfileDao(),
        teamMemberDao = database.teamMemberDao()
    )
}