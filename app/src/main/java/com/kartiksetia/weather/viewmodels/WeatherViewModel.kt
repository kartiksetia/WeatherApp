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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val getForecastDb: GetWeatherFromDbUseCase,
    private val updateForecastDb: UpdateWeatherDbUseCase,
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadData(context: Context){
        if(isNetworkAvailable(context)){
            loadWeatherInfo()
        } else if(isForecastCached()){
            getCachedForecast()
        }
    }
     fun loadWeatherInfo() {
        val cities = ArrayList<CityInfo>()
        cities.add(CityInfo("New York", "New York",40.730610,-73.935242))
        cities.add(CityInfo("Austin", "Texas",30.266666,-97.733330))
        cities.add(CityInfo("Sacramento", "California",38.575764,-121.478851))
        cities.add(CityInfo("Trenton", "New Jersey",40.217052,-74.742935))
        cities.add(CityInfo("Columbus", "Ohio",39.983334,-82.983330))
        val weatherInfoList : MutableList<WeatherInfo> = ArrayList()


            viewModelScope.launch {
                state = state.copy(
                    weatherInfo = null,
                    isLoading = true,
                    error = null
                )
                for (city in cities) {
                when(val result = repository.getWeatherData(city.lat,city.long)) {
                    is Resource.Success -> {
                        var weatherInfo = result.data
                        if (weatherInfo != null) {
                            weatherInfo.city = city
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
                if(state.error == null){
                    state = state.copy(
                        weatherInfo = weatherInfoList,
                        isLoading = false,
                        error = null
                    )
                    updateForecastDb.updateForecastDbUseCase(weatherInfoList)
                }

        }

    }

    private fun isForecastCached(): Boolean {
        return getForecastDb.getForecastFromDbUseCase() != null
    }

    private fun getCachedForecast() {
        state = state.copy(
            weatherInfo = getForecastDb.getForecastFromDbUseCase(),
            isLoading = false,
            error = null
        )

    }

    private suspend fun updateCachedWeather(forecast: List<WeatherInfo>) {
        updateForecastDb.updateForecastDbUseCase(
            forecast
        )
    }
}