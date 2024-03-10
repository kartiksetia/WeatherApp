package com.kartiksetia.weather.presentation.navigation

sealed class Screens(val route : String) {
    object MainScreen : Screens(route = "main_screen")
    object DetailScreen : Screens(route = "detail_screen")
}