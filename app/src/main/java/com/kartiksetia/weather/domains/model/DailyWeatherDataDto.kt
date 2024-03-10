package com.kartiksetia.weather.domains.model

import com.squareup.moshi.Json

data class DailyWeatherDataDto(
    @field:Json(name = "time")  val time: List<String>,
    @field:Json(name = "temperature_2m_max") val temperaturesMax: List<Double>,
    @field:Json(name = "temperature_2m_min") val temperaturesMin: List<Double>,
    @field:Json(name = "weather_code") val weatherCodes: List<Int>,
)

