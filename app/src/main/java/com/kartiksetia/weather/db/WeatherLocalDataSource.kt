package com.kartiksetia.weather.db


import com.kartiksetia.weather.db.entity.ForecastEntity
import com.kartiksetia.weather.db.room.WeatherDao
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
) {

    suspend fun addWeather(weatherEntity: ForecastEntity) =
        weatherDao.addForecastWeather(weatherEntity)


    fun getWeather() = weatherDao.getForecastWeather()

    suspend fun updateWeatherWeather(weatherEntity: ForecastEntity) =
        weatherDao.updateForecastWeather(weatherEntity)

}