package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.AppContainer
import com.example.flightsearch.data.AppdataContainer
import com.example.flightsearch.data.UserSearchRepository

class FlightSearchApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userSearchRepository: UserSearchRepository

    override fun onCreate() {
        super.onCreate()
        container= AppdataContainer(this)
        userSearchRepository= UserSearchRepository(dataStore)
    }
}

private const val USER_SEARCH_PREFERENCE_NAME = "search_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SEARCH_PREFERENCE_NAME
)