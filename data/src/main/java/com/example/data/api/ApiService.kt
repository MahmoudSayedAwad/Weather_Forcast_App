package com.example.data.api

import com.example.data.models.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

        @GET("onecall")
        suspend fun getCurrentWeather(
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("lang") language: String,
            @Query("units") measurementUnit: String,
            @Query("appid")apiKey:String
        ): OneCallResponse
}