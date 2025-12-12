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
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface FavoriteAirportDAO {

    // IGNORE: If the pair exists, do nothing.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteAirport: FavoriteAirport)

    /*
    Triple quotes create a Raw String. This allows you to:
    Write your SQL query over multiple lines so it is easy to read.
    Avoid "escaping" special characters.
     */

    @Query(
        """
            DELETE
            FROM favorite 
                WHERE id=:id
    """
    )
    suspend fun deleteByID(id: Int)

    @Query(
        """
    SELECT 
        favorite.id,
        favorite.departure_code AS departureIATACode,
        favorite.destination_code AS destinationIATACode,
        departure.name AS departureAirport, 
        destination.name AS destinationAirport  
    FROM favorite 
    INNER JOIN airport AS departure 
        ON favorite.departure_code = departure.iata_code 
    INNER JOIN airport AS destination 
        ON favorite.destination_code = destination.iata_code
    """
    )
    fun getFavoriteRoutes(): Flow<List<Route>>

}