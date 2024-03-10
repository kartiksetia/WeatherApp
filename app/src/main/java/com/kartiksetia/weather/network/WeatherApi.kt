package com.kartiksetia.weather.network

import com.kartiksetia.weather.domains.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl,precipitation&daily=temperature_2m_max,temperature_2m_min&current=temperature_2m,precipitation,weather_code,wind_speed_10m")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}