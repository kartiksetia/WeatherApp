package com.kartiksetia.weather.domains.model

import com.squareup.moshi.Json

data class CurrentWeatherDataDto(
    @field:Json(name = "time")  val time: String,
    @field:Json(name = "temperature_2m")  val temperature: Double,
    @field:Json(name = "precipitation")  val precipitation: Double,
    @field:Json(name = "weather_code") val weatherCode: Int,
)
