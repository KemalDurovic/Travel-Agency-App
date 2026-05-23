package com.example.travelagency

import android.app.Application
import com.example.travelagency.model.di.AppContainer

class TravelApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}