package com.kartiksetia.weather.domains.model

data class WeatherState(
    val weatherInfo: List<WeatherInfo>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
