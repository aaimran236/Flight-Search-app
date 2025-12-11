package com.example.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserSearchRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val Search_Term = stringPreferencesKey("search_term")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveSearchedText(searchedText: String) {
        dataStore.edit { preferences ->
            preferences[Search_Term] = searchedText
        }
    }

    val searchedText: Flow<String> = dataStore.data
        .catch {
            ///Using the is keyword to check data type
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                ///By emitting emptyPreferences() if there is an error, the map function can
                // still map to the default value.
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[Search_Term] ?: ""
        }
}