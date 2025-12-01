package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(
    private val airportDAO: AirportDAO,
    private val favoriteAirportDAO: FavoriteAirportDAO
) : AirportRepository {

    override fun getAirportSuggestions(searchTerm: String): Flow<List<AirportInfo>> =
        airportDAO.getSuggestions(searchTerm = searchTerm)

    override fun getFlightsByDeparture(iataCode: String): Flow<List<AirportInfo>> =
        airportDAO.getAvailableFlights(iataCode = iataCode)

    override suspend fun insertFavorite(favoriteAirport: FavoriteAirport) =
        favoriteAirportDAO.insert(favoriteAirport = favoriteAirport)

    override suspend fun deleteFavorite(favoriteAirport: FavoriteAirport) =
        favoriteAirportDAO.delete(favoriteAirport = favoriteAirport)

    override fun getFavoriteRouteList(): Flow<List<Route>> =
        favoriteAirportDAO.getFavoriteRoutes()

}