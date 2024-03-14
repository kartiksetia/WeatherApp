package com.composeweatherapp.core.di

import com.kartiksetia.weather.db.room.WeatherDao
import com.kartiksetia.weather.db.room.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun bindWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

}