package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.mappers.toDomain
import com.example.domain.entities.OneCall
import com.example.domain.entities.Weather
import com.example.domain.repository.Repository

class RepositoryImpl(private val apiService: ApiService):Repository {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String,
        language: String,
        apiKey: String
    ): OneCall =apiService.getCurrentWeather(latitude,longitude,language,measurementUnit,apiKey).toDomain()
}