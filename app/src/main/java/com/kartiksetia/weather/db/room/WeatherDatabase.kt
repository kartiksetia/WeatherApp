package com.kartiksetia.weather.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kartiksetia.weather.db.entity.ForecastEntity

@Database(entities = [ForecastEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}