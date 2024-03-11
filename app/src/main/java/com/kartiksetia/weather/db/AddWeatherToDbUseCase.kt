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
                    weatherInfo[i].weatherDataPerDay,
                    weatherInfo[i].currentWeatherData,
                    weatherInfo[i].currentTemp,
                    weatherInfo[i].currentPrecipitation,
                    weatherInfo[i].currentWeatherType,
                    weatherInfo[i].todayHighTemp,
                    weatherInfo[i].todayLowTemp,
                    weatherInfo[i].city
                )
            )
        }
    }
}