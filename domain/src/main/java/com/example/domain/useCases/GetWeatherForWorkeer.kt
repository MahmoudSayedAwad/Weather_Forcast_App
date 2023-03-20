package com.example.domain.useCases

import com.example.domain.repository.Repository

class GetWeatherForWorkeer(private val repository: Repository) {
    suspend operator fun invoke(latitude:Double, longitude:Double, measurementUnit:String, language:String, apiKey:String) =repository.getWeatherForWorker(latitude,longitude,measurementUnit,language,apiKey)

}