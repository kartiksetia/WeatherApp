package com.kartiksetia.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.kartiksetia.weather.R
import com.kartiksetia.weather.domains.model.WeatherData
import com.kartiksetia.weather.domains.model.WeatherInfo
import com.kartiksetia.weather.presentation.navigation.Screens
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetailWeatherCard(
        weatherInfo: WeatherData,
        backgroundColor: Color,
        modifier: Modifier = Modifier,
    ) {
        weatherInfo.let { data ->
            Spacer(modifier = Modifier.height(5.dp))
            Card(
                onClick = {
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

                        Text(
                            text = data.date,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${data.temperatureCelsius}°C",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        data.windSpeed.roundToInt().let {
                            WeatherDataDisplay(
                                value = it,
                                unit = "°C",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_arrow_down),
                                iconTint = Color.White,
                                textStyle = TextStyle(color = Color.White)
                            )
                        }
                        WeatherDataDisplay(
                            value = data.temperatureCelsius.roundToInt(),
                            unit = "°C",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_arrow_up),
                            iconTint = Color.White,
                            textStyle = TextStyle(color = Color.White)
                        )
                    }
                }
            }
        }

    }