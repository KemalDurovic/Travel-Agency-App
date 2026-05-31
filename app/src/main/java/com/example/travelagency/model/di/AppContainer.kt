package com.example.travelagency.model.di

import android.content.Context
import androidx.room.Room
import com.example.travelagency.model.di.repository.TravelRepository
import com.example.travelagency.model.di.repository.TravelRepositoryImpl
import com.example.travelagency.model.firebase.AuthRepository
import com.example.travelagency.model.firebase.FirestoreRepository
import com.example.travelagency.model.local.db.TravelDatabase
import com.example.travelagency.model.network.NetworkRepository
import com.example.travelagency.model.network.RetrofitInstance

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

    val networkRepository: NetworkRepository = NetworkRepository(RetrofitInstance.api)

    val authRepository: AuthRepository = AuthRepository()

    val firestoreRepository: FirestoreRepository = FirestoreRepository()
}
