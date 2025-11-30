package com.example.flightsearch.data

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface AirportDAO {

    ///Search for autocomplete suggestions in the airport table
    ///Note: || is the string concatenation operator in SQLite
    @Query("SELECT iata_code AS iataCode,name AS fullAirportName FROM airport WHERE iata_code LIKE '%' || :searchTerm || '%' OR name LIKE '%' || :searchTerm || '%' ORDER BY passengers DESC")
    fun getSuggestions(searchTerm: String): Flow<List<AirportInfo>>


    /*Query For list of all possible flights from that airport , When the user selects a
      suggestion
     */

    /*
    every airport has flights to every other airport in the database (except for itself).
     */
    @Query("SELECT iata_code AS iataCode,name AS fullAirportName FROM airport WHERE iata_code!=:iataCode ORDER BY passengers DESC ")
    fun getAvailableFlights(iataCode: String): Flow<List<AirportInfo>>

}