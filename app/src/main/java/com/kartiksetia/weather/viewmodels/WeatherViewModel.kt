package com.kartiksetia.weather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kartiksetia.weather.domains.model.CityInfo
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.domains.model.WeatherState
import com.kartiksetia.weather.network.WeatherRepository
import com.kartiksetia.weather.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

     fun loadWeatherInfo() {
        val cities = ArrayList<CityInfo>()
        cities.add(CityInfo("New York", "New York",40.7128,74.0060))
        cities.add(CityInfo("Austin", "Texas",30.2672,97.7431))
        cities.add(CityInfo("Sacramento", "California",38.5816,121.4944))
        cities.add(CityInfo("Trenton", "New Jersey\n",40.2206,74.7597))
        cities.add(CityInfo("Columbus", "Ohio",39.9612,82.9988))
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
                }

        }

    }
}