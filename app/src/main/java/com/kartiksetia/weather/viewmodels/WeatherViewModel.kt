package com.kartiksetia.weather.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kartiksetia.weather.core.utils.Database.isNetworkAvailable
import com.kartiksetia.weather.domains.model.CityInfo
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.domains.model.WeatherState
import com.kartiksetia.weather.network.WeatherRepository
import com.kartiksetia.weather.core.utils.Resource
import com.kartiksetia.weather.db.GetWeatherFromDbUseCase
import com.kartiksetia.weather.db.UpdateWeatherDbUseCase
import com.kartiksetia.weather.db.AddWeatherToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val getForecastDb: GetWeatherFromDbUseCase,
    private val updateForecastDb: UpdateWeatherDbUseCase,
    private val addForecastDb: AddWeatherToDbUseCase,
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadData(context: Context){
        state = state.copy(
            weatherInfo = null,
            isLoading = true,
            error = null
        )
        if(isNetworkAvailable(context)){
            loadWeatherInfo()
        } else {
            getCachedWeather()
        }
    }
     private fun loadWeatherInfo() {
        val cities = ArrayList<CityInfo>()
        cities.add(CityInfo("New York", "New York",40.730610,-73.935242))
        cities.add(CityInfo("Austin", "Texas",30.266666,-97.733330))
        cities.add(CityInfo("Sacramento", "California",38.575764,-121.478851))
        cities.add(CityInfo("Trenton", "New Jersey",40.217052,-74.742935))
        cities.add(CityInfo("Columbus", "Ohio",39.983334,-82.983330))
        val weatherInfoList : MutableList<WeatherInfo> = ArrayList()

            viewModelScope.launch() {

                state = state.copy(
                    weatherInfo = null,
                    isLoading = true,
                    error = null
                )
                val usersFromApiDeferred = async {
                    cities.forEach { city ->
                        launch { // this will allow us to run multiple tasks in parallel
                            when(val result = repository.getWeatherData(city.lat,city.long,cities.indexOf(city))) {
                                is Resource.Success -> {
                                    val weatherInfo = result.data
                                    if (weatherInfo != null) {
                                        weatherInfo.city = city
                                        weatherInfo.id = cities.indexOf(city)
                                        weatherInfoList.add(weatherInfo)
                                    }
                                }
                                is Resource.Error -> {
                                    state = state.copy(
                                        weatherInfo = null,
                                        isLoading = false,
                                        error = result.message
                                    )
                                }

                            }

                        }
                    }
                }
                usersFromApiDeferred.await()
                if(state.error == null){
                    state = state.copy(
                        weatherInfo = weatherInfoList,
                        isLoading = false,
                        error = null
                    )
                    if (!isWeatherCached()) {
                        cacheWeather(weatherInfoList)
                    } else {
                        updateCachedWeather(weatherInfoList)
                    }
                }

        }

    }

    private fun getCachedWeather() {
        state = if(getForecastDb.getWeatherFromDbUseCase().isEmpty()){
            // can make this in function to avoid code duplication - kartik setia
            state.copy(
                weatherInfo = getForecastDb.getWeatherFromDbUseCase(),
                isLoading = false,
                error = "No Data Available"
            )
        }else{
            state.copy(
                weatherInfo = getForecastDb.getWeatherFromDbUseCase(),
                isLoading = false,
                error = null

            )
        }


    }

    private suspend fun updateCachedWeather(weather: List<WeatherInfo>) {
        updateForecastDb.updateForecastDbUseCase(
            weather
        )
    }

    private fun isWeatherCached(): Boolean {
        return getForecastDb.getWeatherFromDbUseCase().isNotEmpty()
    }
    private suspend fun cacheWeather(weather: List<WeatherInfo>) {
        addForecastDb.addForecastToDbUseCase(
           weatherInfo = weather
        )
    }
}