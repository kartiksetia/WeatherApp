package com.kartiksetia.weather.domains.model


data class WeatherInfo(
    val id : Int,
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    var currentTemp : Double,
    val currentPrecipitation : Double,
    val currentWeatherType : WeatherType,
    val todayHighTemp : Double,
    val todayLowTemp : Double,
    var city : CityInfo
)
