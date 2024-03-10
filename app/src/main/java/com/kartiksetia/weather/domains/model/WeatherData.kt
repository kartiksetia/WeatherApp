package com.kartiksetia.weather.domains.model


import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val windSpeed: Double,
    val weatherType: WeatherType,
)
