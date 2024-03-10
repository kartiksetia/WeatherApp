package com.kartiksetia.weather.domains.model


data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val currentTemp : Double,
    val currentPrecipitation : Double,
    val currentWeatherType : WeatherType,
    val todayHighTemp : Double,
    val todayLowTemp : Double,
    var city : CityInfo
)
