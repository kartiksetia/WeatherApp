package com.kartiksetia.weather.db
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.network.WeatherRepositoryImpl
import javax.inject.Inject

class AddWeatherToDbUseCase @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl) {
    suspend fun addForecastToDbUseCase(weatherInfo: List<WeatherInfo>) {
        for ((i, value) in weatherInfo.withIndex()) {
            weatherRepositoryImpl.addWeather(
                WeatherInfo(
                    i,
                    value.weatherDataPerDay,
                    value.currentWeatherData,
                    value.currentTemp,
                    value.currentPrecipitation,
                    value.currentWeatherType,
                    value.todayHighTemp,
                    value.todayLowTemp,
                    value.city
                )
            )
        }
    }
}