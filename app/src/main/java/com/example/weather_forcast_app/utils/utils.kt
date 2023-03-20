package com.example.weather_forcast_app.utils

import android.content.Context
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE_AR
import com.example.weather_forcast_app.utils.Constants.APPLICATION_LANGUAGE_EN
import java.text.SimpleDateFormat
import java.util.*

fun getDateTime(dt: Int, pattern: String, language: String): String {
    val formatter = SimpleDateFormat(pattern, Locale(language))
    formatter.timeZone = TimeZone.getTimeZone("GMT+2")
    return formatter.format(Date(dt * 1000L))
}

fun getDisplayCurrentDefaultLanguage(): String {
    return if (Locale.getDefault().displayLanguage.equals("العربية")) {
        APPLICATION_LANGUAGE_AR
    } else {
        APPLICATION_LANGUAGE_EN
    }
}

fun getDateTimeLong(milliSeconds: Long, pattern: String, language: String): String {
    val formatter = SimpleDateFormat(pattern, Locale(language))
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}



fun convertDateToLong(date: String,language: String): Long {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale(language))
    val timestamp: Date = simpleDateFormat.parse(date) as Date
    return timestamp.time
}