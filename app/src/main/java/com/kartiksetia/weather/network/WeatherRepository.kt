package com.kartiksetia.weather.network

import com.kartiksetia.weather.core.utils.Resource
import com.kartiksetia.weather.domains.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double, id: Int): Resource<WeatherInfo>

    suspend fun addWeather(weather: WeatherInfo)

    fun getWeather() : List<WeatherInfo>?

    suspend fun updateWeather(weather: WeatherInfo)

}