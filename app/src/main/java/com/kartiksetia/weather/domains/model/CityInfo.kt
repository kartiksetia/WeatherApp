package com.kartiksetia.weather.domains.model

data class CityInfo(
    val cityName: String,
    val stateName: String?,
    val lat : Double,
    val long : Double,
){
    init {

    }
}
