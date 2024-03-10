package com.kartiksetia.weather.presentation.main

import androidx.compose.runtime.Composable
import com.kartiksetia.weather.presentation.navigation.NavGraph
import com.kartiksetia.weather.viewmodels.WeatherViewModel

@Composable
fun App(viewModel: WeatherViewModel){
    NavGraph(viewModel = viewModel)
}