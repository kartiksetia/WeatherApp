package com.kartiksetia.weather.domains.mapper

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.kartiksetia.weather.domains.model.CityInfo
import com.kartiksetia.weather.domains.model.WeatherData
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.domains.model.WeatherType
import java.lang.reflect.Type

class WeatherDeserializer : JsonDeserializer<WeatherInfo> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): WeatherInfo {
        val customerObject = json.asJsonObject
        val weatherDataObject = customerObject.get("weatherDataPerDay").asJsonObject
        val weatherDataArray = weatherDataObject.get("0").asJsonArray

        val itemType = object : TypeToken<List<WeatherData>>() {}.type
        val weatherList: List<WeatherData> = Gson().fromJson(weatherDataArray, itemType)

        val perDay = mutableMapOf<Int, List<WeatherData>>()
        perDay[0] = weatherList
        return WeatherInfo(
            5,
            weatherDataPerDay = perDay,
            currentWeatherData = null,
            currentTemp = 1.1,
            currentPrecipitation = 1.1,
            currentWeatherType = WeatherType.fromWMO(73),
            todayHighTemp = 1.1,
            todayLowTemp = 1.1,
            city = CityInfo("","",0.0,0.0)


        )
    }
}

