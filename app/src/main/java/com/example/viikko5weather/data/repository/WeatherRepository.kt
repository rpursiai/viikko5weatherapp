package com.example.viikko5weather.data.repository

import com.example.viikko5weather.data.local.WeatherDao
import com.example.viikko5weather.data.model.WeatherEntity
import com.example.viikko5weather.data.remote.WeatherApi
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val dao: WeatherDao,
    private val api: WeatherApi,
    private val apiKey: String
) {
    fun observeLatest(): Flow<WeatherEntity?> = dao.observeLatest()

    suspend fun refreshIfNeeded(city: String, maxAgeMinutes: Long = 30) {
        val now = System.currentTimeMillis()
        val cached = dao.getLatestOnce()

        val isSameCity = cached?.city?.equals(city, ignoreCase = true) == true
        val isFresh = cached != null && (now - cached.fetchedAtMillis) <= maxAgeMinutes * 60_000

        if (cached != null && isSameCity && isFresh) return

        val response = api.getWeatherByCity(city = city, apiKey = apiKey)

        val w = response.weather.firstOrNull()
        val entity = WeatherEntity(
            id = 1,
            city = response.name,
            tempC = response.main.temp,
            main = w?.main.orEmpty(),
            description = w?.description.orEmpty(),
            icon = w?.icon.orEmpty(),
            fetchedAtMillis = now
        )
        dao.upsert(entity)
    }
}