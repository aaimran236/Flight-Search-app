package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.AirportInfo
import com.example.flightsearch.data.FavoriteAirport
import com.example.flightsearch.data.Route

@Composable
fun FavoriteRoutes(
    title: String,
    favoriteRouteLIst: List<Route>,
    onRemoveClick:(Int)-> Unit,
    isFavorite: Boolean,
    modifier: Modifier= Modifier
) {
    Column(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxSize(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn() {
            items(favoriteRouteLIst, key = { it.id }) { route ->
                flightItem(
                    id = route.id,
                    departure = AirportInfo(
                        iataCode = route.departureIATACode,
                        fullAirportName = route.departureAirport
                    ),
                    destination = AirportInfo(
                        iataCode = route.destinationIATACode,
                        fullAirportName = route.destinationAirport
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onFavoriteClick = {},
                    onRemoveClick = onRemoveClick,
                    isFavorite = isFavorite
                )
            }
        }
    }
}