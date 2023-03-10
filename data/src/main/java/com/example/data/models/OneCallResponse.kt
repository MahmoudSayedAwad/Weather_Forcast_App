package com.example.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.utils.Constants

@Entity(tableName = Constants.oneCallTableName)
data class OneCallResponse(
    @ColumnInfo(name = "current")
    var current: CurrentResponse?,
    @ColumnInfo(name = "daily")
    var daily: List<DailyResponse>?,
    @ColumnInfo(name = "hourly")
    var hourly: List<HourlyResponse>?,
    @ColumnInfo(name = "lat")
    var lat: Double?,
    @ColumnInfo(name = "lon")
    var lon: Double?,
    @ColumnInfo(name = "minutely")
    var minutely: List<MinutelyResponse>?,
    @ColumnInfo(name = "time_zone")
    var timezone: String?,
    @PrimaryKey
    @ColumnInfo(name = "timezone_offset")
    var timezone_offset: Int?,
    @ColumnInfo(name = "alerts")
    var alerts: List<AlertResponse>?
)