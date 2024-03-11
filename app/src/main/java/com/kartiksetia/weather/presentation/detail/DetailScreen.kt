package com.kartiksetia.weather.presentation.detail


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kartiksetia.weather.R
import com.kartiksetia.weather.core.system.theme.BackgroundColorCard1
import com.kartiksetia.weather.domains.mapper.WeatherDeserializer
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.presentation.components.DetailWeatherCard

@Composable
fun DetailScreen(weatherString: String){

    val gson = GsonBuilder().registerTypeAdapter(WeatherInfo::class.java, WeatherDeserializer()).create()

    val weatherInfo = gson.fromJson(weatherString, WeatherInfo::class.java)

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.ic_back2),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                weatherInfo.weatherDataPerDay[0].let {
                    items(it!!.take(5).size ) { item ->
                        DetailWeatherCard(
                            weatherInfo = weatherInfo.weatherDataPerDay[0]!![item],
                            backgroundColor = BackgroundColorCard1
                        )
                    }
                }
            }

        }
    }
}