package com.example.flightsearch.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                inventoryApplication().container.airportRepository,
                inventoryApplication().userSearchRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [FlightSearchApplication].
 */

fun CreationExtras.inventoryApplication(): FlightSearchApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)