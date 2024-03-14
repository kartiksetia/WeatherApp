package com.kartiksetia.weather.db

import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.network.WeatherRepositoryImpl
import javax.inject.Inject

class GetWeatherFromDbUseCase @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl) {
    fun getWeatherFromDbUseCase() : List<WeatherInfo> = weatherRepositoryImpl.getWeather()
}