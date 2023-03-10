package com.example.data.db

import androidx.room.TypeConverter
import com.example.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun convertToCurrent(value: String): CurrentResponse {

        return Gson().fromJson(value, CurrentResponse::class.java)
    }

    @TypeConverter
    fun convertCurrentToString(current: CurrentResponse): String = Gson().toJson(current)

    @TypeConverter
    fun convertToWeatherList(value: String): List<WeatherResponse> {
        val type: Type = object : TypeToken<List<WeatherResponse>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertWeatherListToString(list: List<WeatherResponse>): String = Gson().toJson(list)

    @TypeConverter
    fun convertToDailyList(value: String): List<DailyResponse> {
        val type: Type = object : TypeToken<List<DailyResponse>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertDailyListToString(list: List<DailyResponse>): String = Gson().toJson(list)

    @TypeConverter
    fun convertToTemp(value: String): TempResponse {

        return Gson().fromJson(value, TempResponse::class.java)
    }

    @TypeConverter
    fun convertTempToString(list: TempResponse): String = Gson().toJson(list)

    @TypeConverter
    fun convertToHourlyList(value: String): List<HourlyResponse> {
        val type: Type = object : TypeToken<List<HourlyResponse>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertHourlyListToString(list: List<HourlyResponse>): String = Gson().toJson(list)

    @TypeConverter
    fun convertToAlertList(value: String): List<AlertResponse>? {
        val type: Type = object : TypeToken<List<AlertResponse>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertAlertListToString(list: List<AlertResponse>?): String = Gson().toJson(list)

    @TypeConverter
    fun convertToAlertTagList(value: String): List<String> {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertAlertTagListToString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun convertToMinutelyList(value: String): List<MinutelyResponse>? {
        val type: Type = object : TypeToken<List<MinutelyResponse>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun convertMinutelyListToString(list: List<MinutelyResponse>?): String = Gson().toJson(list)


}
