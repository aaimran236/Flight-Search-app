package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

    /*
    Sometimes, certain fields or groups of fields in a database must be
    unique. We can enforce this uniqueness property by setting the unique
    property of an @Index annotation to true.
    */

@Entity(
    tableName = "favorite",
    indices = [Index(
        value = ["departure_code", "destination_code"],
        unique = true
    )]
)

data class FavoriteAirport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "departure_code")
    val departureIATACode: String,

    @ColumnInfo(name = "destination_code")
    val destinationIATACode: String
)