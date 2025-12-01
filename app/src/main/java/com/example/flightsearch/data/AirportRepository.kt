package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAirportSuggestions(searchTerm: String): Flow<List<AirportInfo>>

    fun getFlightsByDeparture(iataCode: String): Flow<List<AirportInfo>>

    suspend fun insertFavorite(favoriteAirport: FavoriteAirport)

    suspend fun deleteFavorite(favoriteAirport: FavoriteAirport)

    fun getFavoriteRouteList(): Flow<List<Route>>
}