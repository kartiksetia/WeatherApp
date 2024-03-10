package com.kartiksetia.weather.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kartiksetia.weather.presentation.detail.DetailScreen
import com.kartiksetia.weather.presentation.home.HomeScreen
import com.kartiksetia.weather.viewmodels.WeatherViewModel

@Composable
fun NavGraph(
    viewModel: WeatherViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screens.MainScreen.route
        ) {
            composable(Screens.MainScreen.route) {
                HomeScreen(viewModel, navController = navController)
            }
            composable(Screens.DetailScreen.route) {
                DetailScreen(viewModel.state.weatherInfo!![0], navController)
            }
        }
    }
}