package com.kartiksetia.weather.db
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.network.WeatherRepositoryImpl
import javax.inject.Inject

class AddWeatherToDbUseCase @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl) {
     suspend fun addForecastToDbUseCase(weatherInfo: List<WeatherInfo>) {
        for (value : WeatherInfo in weatherInfo) {
            weatherRepositoryImpl.addWeather(
                WeatherInfo(
                    value.id,
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