package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.utils.Constants

@Entity(tableName = Constants.favouriteCityTableName)
data class FavouriteCity(
    @PrimaryKey val cityName: String,
    val cityNameAR: String,
    val latitude: Double,
    val longitude: Double
)