package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport (
    @PrimaryKey
    val id: Int=0,

    @ColumnInfo(name="iata_code")
    val iATACode: String,

    @ColumnInfo(name="name")
    val fullAirportName: String,

    @ColumnInfo(name="passengers")
    val numberOfPassengersPerYear: Int

)