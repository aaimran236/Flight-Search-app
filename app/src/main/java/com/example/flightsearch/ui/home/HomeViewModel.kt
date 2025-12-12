/*
Copyright (C) 2025 aaimran236

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.example.flightsearch.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.AirportInfo
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.FavoriteAirport
import com.example.flightsearch.data.Route
import com.example.flightsearch.data.UserSearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val airportRepository: AirportRepository,
    private val userSearchRepository: UserSearchRepository
) : ViewModel() {

    private val _queryFlow = MutableStateFlow("")
    val searchQuery=_queryFlow.asStateFlow()
    var resultUiState by mutableStateOf(ResultUiState())
        private set

    init {
        viewModelScope.launch {
            val savedSearched=userSearchRepository.searchedText.first()
            if (savedSearched.isNotEmpty()){
                onQueryChanged(savedSearched)
            }
        }
    }

    fun onQueryChanged(query: String) {
        resultUiState= resultUiState.copy(isDepartureSelected = false)
        _queryFlow.value = query
        viewModelScope.launch {
            userSearchRepository.saveSearchedText(query)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestionUiState: StateFlow<SuggestionUiState> =

        _queryFlow.flatMapLatest { query ->
            // 2. This block runs every time 'query' changes
            if (query.isBlank()) {
                flowOf(emptyList()) // Return empty if text is blank
            } else {
                airportRepository.getAirportSuggestions(query)
            }
        }
            .map { SuggestionUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SuggestionUiState()
            )

//    val availableFlightsUiState: StateFlow<AvailableFlightsUiState> =
//        airportRepository.getFlightsByDeparture(resultUiState.departureIATA)
//            .map { AvailableFlightsUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started =  SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = AvailableFlightsUiState()
//            )

    fun getListOfAvailableFlights(iataCode: String = resultUiState.departureInfo.iataCode): Flow<List<AirportInfo>> =
        airportRepository.getFlightsByDeparture(iataCode)

    val favoriteRouteUiState : StateFlow<FavoriteRouteUiState> =
        airportRepository.getFavoriteRouteList()
            .map { FavoriteRouteUiState(it) }
            .stateIn(
                scope=viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoriteRouteUiState()
            )

    fun updateDeparture(departureIATA: String, departureName: String) {
        onQueryChanged(departureIATA)

        resultUiState= resultUiState.copy(
            departureInfo = AirportInfo(departureIATA,departureName),
            isDepartureSelected = true)
    }


    fun addToFavorites(destinationIATACode: String) {

        val favoriteAirport =
            FavoriteAirport(
                departureIATACode = resultUiState.departureInfo.iataCode,
                destinationIATACode = destinationIATACode
            )

        viewModelScope.launch {
            airportRepository.insertFavorite(favoriteAirport)
        }
    }

    fun removeFromFavorites(favoriteId: Int){
        viewModelScope.launch {
            airportRepository.deleteFromFavorite(id=favoriteId)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class SuggestionUiState(
    val suggestionList: List<AirportInfo> = emptyList(),
)

//data class AvailableFlightsUiState(
//    val availableFlights: List<AirportInfo> = emptyList(),
//)

data class FavoriteRouteUiState(
    val routeList: List<Route> =emptyList()
)

data class ResultUiState(
    val departureInfo: AirportInfo= AirportInfo("",""),
    val isDepartureSelected: Boolean = false,
)