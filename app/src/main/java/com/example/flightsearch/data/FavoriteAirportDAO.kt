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

    @Delete
    suspend fun delete(favoriteAirport: FavoriteAirport)

    /*
    Triple quotes create a Raw String. This allows you to:
    Write your SQL query over multiple lines so it is easy to read.
    Avoid "escaping" special characters.
     */

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