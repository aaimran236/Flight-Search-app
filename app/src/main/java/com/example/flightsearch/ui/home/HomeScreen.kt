package com.example.flightsearch.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.AirportInfo
import com.example.flightsearch.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.suggestionUiState.collectAsState()
    val searchTerm by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            FlightSearchTopAppBar()
        }
    ) { innerPadding ->
        searchFlight(
            searchText = searchTerm,
            onValueChange = viewModel::onQueryChanged,
            suggestions = uiState.suggestionList,
            contentPadding = innerPadding
        )
    }
}

@Composable
fun searchFlight(
    searchText: String,
    onValueChange:(String)-> Unit,
    suggestions: List<AirportInfo>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){
    Column(modifier = Modifier.padding(contentPadding) ) {
        OutlinedTextField(
            value = searchText,
            onValueChange=onValueChange,
            placeholder = { Text(stringResource(R.string.search_flight)) },
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//        ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            shape = RoundedCornerShape(50),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },

            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer, // Light blue-ish background
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent, // Hide the outline line if you want purely flat look
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 2.dp)
        ) {
            items(suggestions, key = { it.iataCode }) {
                    suggestion->
                FlightSuggestion(
                    suggestion=suggestion,
                    modifier=Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FlightSuggestion(
    suggestion: AirportInfo,
    modifier: Modifier= Modifier
){
    Row(
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = suggestion.iataCode,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,

            ///modifier = Modifier.width(40.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = suggestion.fullAirportName,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = 1, // Truncate if too long
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
    )
}