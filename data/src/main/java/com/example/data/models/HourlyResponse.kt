package com.example.data.models

data class HourlyResponse(
    var clouds: Int?,
    var dew_point: Double?,
    var dt: Int?,
    var feels_like: Double?,
    var humidity: Int?,
    var pop: Double?,
    var pressure: Int?,
    var rain: RainResponse?,
    var temp: Double?,
    var uvi: Double?,
    var visibility: Int?,
    var weather: List<WeatherResponse>?,
    var wind_deg: Int?,
    var wind_gust: Double?,
    var wind_speed: Double?
)