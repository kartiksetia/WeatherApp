package com.kartiksetia.weather.db.room

import androidx.room.*
import com.kartiksetia.weather.db.entity.ForecastEntity

@Dao
interface WeatherDao {
    @Insert
    suspend fun addForecastWeather(forecastEntity: ForecastEntity)

    @Query("SELECT * FROM weather_data")
    fun getForecastWeather(): List<ForecastEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateForecastWeather(forecastEntity: ForecastEntity)
}