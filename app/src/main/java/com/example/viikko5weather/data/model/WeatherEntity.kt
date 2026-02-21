package com.example.viikko5weather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val id: Int = 1,
    val city: String,
    val tempC: Double,
    val main: String,
    val description: String,
    val icon: String,
    val fetchedAtMillis: Long
)