package com.example.weather_forcast_app.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDateTime(dt: Int, pattern: String,language:String): String {
    val formatter = SimpleDateFormat(pattern, Locale(language))
    formatter.timeZone = TimeZone.getTimeZone("GMT+2")
    return formatter.format(Date(dt * 1000L))
}