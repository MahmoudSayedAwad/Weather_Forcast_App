package com.example.domain.entities

data class Hourly(
    var clouds: Int,
    var dew_point: Double,
    var dt: Int,
    var feels_like: Double,
    var humidity: Int,
    var pop: Double,
    var pressure: Int,
    var rain: Rain,
    var temp: Double,
    var uvi: Double,
    var visibility: Int,
    var weather: List<Weather>,
    var wind_deg: Int,
    var wind_gust: Double,
    var wind_speed: Double
)