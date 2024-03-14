package com.kartiksetia.weather.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kartiksetia.weather.core.utils.Database

@Entity(tableName = Database.weather_table)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "temp")
    var temp: Double,

    @ColumnInfo(name = "high")
    var high: Double,

    @ColumnInfo(name = "low")
    var low: Double,

    @ColumnInfo(name = "prep")
    var prep: Double,

    @ColumnInfo(name = "wind")
    var wind: Double,

    @ColumnInfo(name = "weather_code")
    var code: Int,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "city")
    var city: String,
)
