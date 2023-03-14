package com.example.domain.repository

import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.entities.OneCall
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.utils.ResultResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String,
        language: String,
        apiKey: String
    ): Flow<ResultResponse<OneCall>>

    suspend fun getAllFavouriteCities(): Flow<ResultResponse<List<FavouriteCityEntity>>>
    suspend fun addCityToFavourite(favouriteCity: FavouriteCityEntity): Flow<ResultResponse<Unit>>
    suspend fun deleteCityFromFavourite(favouriteCity: FavouriteCityEntity): Flow<ResultResponse<Unit>>

    suspend fun insertOneCallModel(oneCallModel: OneCall): Flow<ResultResponse<Unit>>

    suspend fun deleteOneCallModel(oneCallModel: OneCall): Flow<ResultResponse<Unit>>

    suspend fun getUserAlerts(): Flow<ResultResponse<List<UserAlertsEntity>>>

    suspend fun insertUserAlerts(userAlerts: UserAlertsEntity): Flow<ResultResponse<Unit>>

    suspend fun deleteUserAlerts(userAlerts: UserAlertsEntity): Flow<ResultResponse<Unit>>
}