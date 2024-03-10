package com.kartiksetia.weather.domains.model

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly") val hourlyWeatherData: HourlyWeatherDataDto,

    @field:Json(name = "current") val currentWeatherData: CurrentWeatherDataDto,

    @field:Json(name = "daily") val dailyWeatherData: DailyWeatherDataDto
)
