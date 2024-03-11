package com.kartiksetia.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kartiksetia.weather.R
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.presentation.navigation.Screens
import com.kartiksetia.weather.viewmodels.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWeatherCard(
    viewModel : WeatherViewModel,
    weatherInfo: WeatherInfo,
    backgroundColor: Color,
    navController : NavHostController,
    modifier: Modifier = Modifier,
) {
    weatherInfo.let { data ->
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            onClick = {
                val gson = GsonBuilder().create()
                navController.navigate(Screens.DetailScreen.withArgs(gson.toJson(weatherInfo).toString())
                )
                           },
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    data.currentWeatherData?.time?.let {
                        Text(
                            text = it.format(
                                DateTimeFormatter.ofPattern("HH:mm a")
                            ),
                            fontSize = 12.sp,
                            modifier = Modifier.weight(7f),
                            color = Color.White
                        )
                    }

                    Text(
                        text = "Today",
                        modifier = Modifier.weight(1.5f),
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = data.currentWeatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${data.currentTemp}°C",
                    fontSize = 30.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${data.city.cityName} , ${data.city.stateName}",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(7.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    data.todayLowTemp.roundToInt().let {
                        WeatherDataDisplay(
                            value = it,
                            unit = "°C",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_arrow_down),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                    WeatherDataDisplay(
                        value = data.todayHighTemp.roundToInt(),
                        unit = "°C",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_arrow_up),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    data.currentPrecipitation.let {
                        WeatherDataDisplay(
                            value = it.roundToInt(),
                            unit = "%",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                    data.currentWeatherData?.windSpeed?.let {
                        WeatherDataDisplay(
                            value = it.roundToInt(),
                            unit = "km/h",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                }
            }
        }
    }

}