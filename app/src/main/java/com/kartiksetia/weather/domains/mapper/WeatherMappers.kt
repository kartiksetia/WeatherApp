package com.kartiksetia.weather.domains.mapper

import com.kartiksetia.weather.db.entity.ForecastEntity
import com.kartiksetia.weather.domains.model.CityInfo
import com.kartiksetia.weather.domains.model.DailyWeatherDataDto
import com.kartiksetia.weather.domains.model.HourlyWeatherDataDto
import com.kartiksetia.weather.domains.model.WeatherDto
import com.kartiksetia.weather.domains.model.WeatherData
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.domains.model.WeatherType
import org.w3c.dom.Entity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun DailyWeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperaturesMax = temperaturesMax[index]
        val temperaturesMin = temperaturesMin[index]
        val date = time

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.now(),
                temperatureCelsius = temperaturesMax,
                windSpeed = temperaturesMin,
                date =  date
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val dailyWeatherDataMap = dailyWeatherData.toWeatherDataMap()
    val currentTemp = currentWeatherData.temperature
    val currentPrecipitation = currentWeatherData.precipitation
    val currentWeatherCode = currentWeatherData.weatherCode
    val todayHighTemp = dailyWeatherData.temperaturesMax[0]
    val todayLowTemp = dailyWeatherData.temperaturesMin[0]
    val now = LocalDateTime.now()
    val currentWeatherData = dailyWeatherDataMap[0]?.get(0)

    return WeatherInfo(
        5,
        weatherDataPerDay = dailyWeatherDataMap,
        currentWeatherData = currentWeatherData,
        currentTemp = currentTemp,
        currentPrecipitation = currentPrecipitation,
        currentWeatherType = WeatherType.fromWMO(currentWeatherCode),
        todayHighTemp = todayHighTemp,
        todayLowTemp = todayLowTemp,
        city = CityInfo("","",0.0,0.0)

    )
}

fun EntitytoWeatherInfo(forecastEntity: List<ForecastEntity>): List<WeatherInfo> {
    val weatherInfos : MutableList<WeatherInfo> = ArrayList()
    for (forecastEntityNew : ForecastEntity in forecastEntity){
        val city = forecastEntityNew.city
        val weatherInfo  : WeatherInfo = WeatherInfo(
            5,
            weatherDataPerDay = mutableMapOf<Int, List<WeatherData>>(),
            currentWeatherData = null,
            currentTemp = forecastEntityNew.temp,
            currentPrecipitation = forecastEntityNew.prep,
            currentWeatherType = WeatherType.fromWMO(forecastEntityNew.code),
            todayHighTemp = forecastEntityNew.high,
            todayLowTemp = forecastEntityNew.low,
            city = CityInfo(city,"",0.0,0.0)
        )
        weatherInfos.add(weatherInfo)
    }

    return weatherInfos
}

fun entityFromModel(model: WeatherInfo): ForecastEntity {
    return ForecastEntity(
        id = 5,
        temp = model.currentTemp,
        high = model.todayHighTemp,
        low = model.todayLowTemp,
        prep = model.currentPrecipitation,
        wind = model.currentWeatherData?.windSpeed ?: 0.0,
        code = model.currentWeatherType.iconRes,
        date = model.currentWeatherData?.date ?: "Today",
        time = model.currentWeatherData?.time.toString(),
        city = model.city.cityName

    )
}

