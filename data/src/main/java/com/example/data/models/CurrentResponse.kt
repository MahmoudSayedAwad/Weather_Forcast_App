package com.example.data.models

data class CurrentResponse(
    var clouds: Int?,
    var dew_point: Double?,
    var dt: Int?,
    var feels_like: Double?,
    var humidity: Int?,
    var pressure: Int?,
    var sunrise: Int?,
    var sunset: Int?,
    var temp: Double?,
    var uvi: Double?,
    var visibility: Int?,
    var weather: List<WeatherResponse?>?,
    var wind_deg: Int?,
    var wind_speed: Double?
)