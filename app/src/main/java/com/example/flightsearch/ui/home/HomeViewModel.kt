package com.example.flightsearch.ui.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.AirportInfo
import com.example.flightsearch.data.AirportRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(val airportRepository: AirportRepository) : ViewModel() {

    private val _queryFlow = MutableStateFlow("")
    val searchQuery=_queryFlow.asStateFlow()

     var resultUiState by mutableStateOf(ResultUiState())
         private set

    fun onQueryChanged(query: String) {
        resultUiState= resultUiState.copy(isDepartureSelected = false)
        _queryFlow.value = query
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

    fun getListOfAvailableFlights(iataCode: String = resultUiState.airportInfo.iataCode): Flow<List<AirportInfo>> =
        airportRepository.getFlightsByDeparture(iataCode)

    fun updateResultUi(departureIATA: String, departureName: String) {
        onQueryChanged(departureIATA)

        resultUiState= resultUiState.copy(
            airportInfo = AirportInfo(departureIATA,departureName),
            isDepartureSelected = true)
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

data class ResultUiState(
    val airportInfo: AirportInfo= AirportInfo("",""),
    val isDepartureSelected: Boolean = false
)