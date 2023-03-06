package com.example.data.models

data class OneCallResponse(
    var current: CurrentResponse?,
    var daily: List<DailyResponse>?,
    var hourly: List<HourlyResponse>?,
    var lat: Double?,
    var lon: Double?,
    var minutely: List<MinutelyResponse>?,
    var timezone: String?,
    var timezone_offset: Int?
)