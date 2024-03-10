package com.kartiksetia.weather.domains.mapper

import com.kartiksetia.weather.domains.model.CityInfo
import com.kartiksetia.weather.domains.model.DailyWeatherDataDto
import com.kartiksetia.weather.domains.model.HourlyWeatherDataDto
import com.kartiksetia.weather.domains.model.WeatherDto
import com.kartiksetia.weather.domains.model.WeatherData
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.domains.model.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun HourlyWeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                windSpeed = windSpeed,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun DailyWeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperaturesMax = temperaturesMax[index]
        val weatherCode = weatherCodes[index]
        val temperaturesMin = temperaturesMin[index]

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperaturesMax,
                windSpeed = temperaturesMin,
                weatherType = WeatherType.fromWMO(weatherCode)
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
    val currentWeatherData = dailyWeatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
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

