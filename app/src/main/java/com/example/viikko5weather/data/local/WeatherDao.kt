package com.example.viikko5weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.viikko5weather.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_cache WHERE id = 1 LIMIT 1")
    fun observeLatest(): Flow<WeatherEntity?>

    @Query("SELECT * FROM weather_cache WHERE id = 1 LIMIT 1")
    suspend fun getLatestOnce(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherEntity)
}