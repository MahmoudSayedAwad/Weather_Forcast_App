package com.example.domain.repository

import com.example.domain.entities.OneCall
import com.example.domain.entities.Weather

interface Repository {
   suspend fun getCurrentWeather(latitude:Double,longitude:Double,measurementUnit:String,language:String,apiKey:String): OneCall
}