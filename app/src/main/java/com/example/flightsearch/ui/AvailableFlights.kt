package com.example.flightsearch.ui



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.AirportInfo

@Composable
fun  allPossibleFlights(
    modifier: Modifier=Modifier,
    departureInfo: AirportInfo,
    availableFlights: List<AirportInfo>,
    onFavoriteClick:()-> Unit,
    ///TODO: add isFavorite: Boolean
) {
    Column(
        modifier= Modifier
            .padding(
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxSize(),
    ){
        Text(
            text="Flight from ${departureInfo.iataCode}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn() {
            items(availableFlights, key = { it.iataCode }) { flight ->
                flightItem(
                    departure = departureInfo,
                    destination = flight,
                    modifier=Modifier.fillMaxWidth(),
                    onFavoriteClick=onFavoriteClick,

                )
            }
        }
    }
}

@Composable
fun flightItem(
    departure: AirportInfo,
    destination: AirportInfo,
    modifier: Modifier=Modifier,
    onFavoriteClick:()->Unit,
    isFavorite: Boolean=true
){
    Card(
        shape = RoundedCornerShape(topStart = 0.dp,
            topEnd = 20.dp, // Only this one is rounded
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
            .padding(5.dp)
            .size(105.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(7.dp), // Add padding inside the card
            verticalAlignment = Alignment.CenterVertically // Align Star with Text center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                routeInfo(
                    label = stringResource(R.string.departure),
                    route = departure
                )
                Spacer(Modifier.height(5.dp))
                routeInfo(
                    label = stringResource(R.string.destination),
                    route = destination
                )
            }

            IconButton(onClick = onFavoriteClick,
                enabled = false,
                modifier= Modifier.fillMaxHeight()) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color(0xFFD4AF37) else Color.Gray // Gold if favorite
                )
            }
        }
    }
}

@Composable
fun routeInfo(
    label: String,
    route: AirportInfo
){
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
    )

    Row {
        Text(
            text = route.iataCode,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = route.fullAirportName,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = 1,
            ///overflow = TextOverflow.Ellipsis
        )
    }
}