package com.example.data.models

data class DailyResponse(
    var clouds: Int?,
    var dew_point: Double?,
    var dt: Int?,
    var feels_like: FeelsLikeResponse?,
    var humidity: Int?,
    var moon_phase: Double?,
    var moonrise: Int?,
    var moonset: Int?,
    var pop: Double?,
    var pressure: Int?,
    var rain: Double?,
    var sunrise: Int?,
    var sunset: Int?,
    var temp: TempResponse?,
    var uvi: Double?,
    var weather: List<WeatherResponse>?,
    var wind_deg: Int?,
    var wind_gust: Double?,
    var wind_speed: Double?
)