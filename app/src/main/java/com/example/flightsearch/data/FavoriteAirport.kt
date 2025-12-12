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