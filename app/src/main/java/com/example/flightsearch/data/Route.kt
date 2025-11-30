package com.example.flightsearch.data


data class Route(
    val id: Int=0,
    val departureIATACode: String,
    val destinationIATACode: String,
    val departureAirport: String,
    val destinationAirport: String
)