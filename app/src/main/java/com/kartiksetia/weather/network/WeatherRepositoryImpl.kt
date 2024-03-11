package com.kartiksetia.weather.network


import com.kartiksetia.weather.domains.mapper.toWeatherInfo
import com.kartiksetia.weather.core.utils.Resource
import com.kartiksetia.weather.db.WeatherLocalDataSource
import com.kartiksetia.weather.domains.mapper.EntitytoWeatherInfo
import com.kartiksetia.weather.domains.mapper.entityFromModel
import com.kartiksetia.weather.domains.model.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val forecastLocalDataSource: WeatherLocalDataSource,
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double, id: Int): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo(id)
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun addWeather(weather: WeatherInfo) {
        forecastLocalDataSource.addWeather(
            entityFromModel(weather)
        )
    }

    override fun getWeather(): List<WeatherInfo>{
        return if (forecastLocalDataSource.getWeather().isEmpty()) emptyList() else {
            EntitytoWeatherInfo(forecastLocalDataSource.getWeather())
        }
    }

    override suspend fun updateWeather(weather: WeatherInfo) {
        forecastLocalDataSource.updateWeatherWeather(
            entityFromModel(weather)
        )
    }
}