package com.example.flightsearch.data

import android.content.Context

interface AppContainer {
    val airportRepository: AirportRepository
}

class AppdataContainer(private val context: Context) : AppContainer {

    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(
            AirportDatabase.getDatabase(context = context).airportDAO(),
            AirportDatabase.getDatabase(context = context).favoriteAirportsDAO()
        )
    }
}