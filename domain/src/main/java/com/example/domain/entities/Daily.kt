package com.example.domain.entities

data class Daily(
    var clouds: Int,
    var dew_point: Double,
    var dt: Int,
    var feels_like: FeelsLike,
    var humidity: Int,
    var moon_phase: Double,
    var moonrise: Int,
    var moonset: Int,
    var pop: Double,
    var pressure: Int,
    var rain: Double,
    var sunrise: Int,
    var sunset: Int,
    var temp: Temp,
    var uvi: Double,
    var weather: List<Weather>,
    var wind_deg: Int,
    var wind_gust: Double,
    var wind_speed: Double
)