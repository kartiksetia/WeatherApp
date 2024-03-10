package com.kartiksetia.weather.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.kartiksetia.weather.core.system.theme.MyApplicationTheme
import com.kartiksetia.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.loadWeatherInfo()
        setContent {
            MyApplicationTheme {
                App(viewModel = viewModel)
            }
        }
    }
}

