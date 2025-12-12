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
package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDAO {

    /*
    Search for autocomplete suggestions in the airport table
     */

    ///Note: || is the string concatenation operator in SQLite
    @Query(
        """
            SELECT 
                iata_code AS iataCode,
                name AS fullAirportName 
            FROM airport 
            WHERE iata_code LIKE '%' || :searchTerm || '%' 
                OR name LIKE '%' || :searchTerm || '%' 
            ORDER BY passengers DESC"""
    )
    fun getSuggestions(searchTerm: String): Flow<List<AirportInfo>>


    /*Query For list of all possible flights from that airport , When the user selects a
      suggestion
     */

    /*
    every airport has flights to every other airport in the database (except for itself).
     */
    @Query(
        """
            SELECT 
                iata_code AS iataCode,
                name AS fullAirportName 
            FROM airport 
            WHERE iata_code!=:iataCode 
            ORDER BY passengers DESC """
    )
    fun getAvailableFlights(iataCode: String): Flow<List<AirportInfo>>

}