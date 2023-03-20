package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.db.AppDatabase
import com.example.data.mappers.fromDomain
import com.example.data.mappers.toDomain
import com.example.data.models.FavouriteCity
import com.example.data.utils.Constants
import com.example.data.utils.NetworkConnectivityChecker
import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.entities.OneCall
import com.example.domain.entities.UserAlertsEntity
import com.example.domain.repository.Repository
import com.example.domain.utils.ResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val networkConnectivityChecker: NetworkConnectivityChecker
) : Repository {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        measurementUnit: String,
        language: String,
        apiKey: String
    ): Flow<ResultResponse<OneCall>> = flow {
        if (networkConnectivityChecker.checkForInternet()) {
            emit(ResultResponse.OnLoading(true))
            emit(
                ResultResponse.OnSuccess(
                    apiService.getCurrentWeather(
                        latitude, longitude, language, measurementUnit, apiKey
                    ).toDomain()
                )
            )
        } else {
            // emit(ResultResponse.OnError("No internet connection"))
            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.oneCallDao().getOneCallModel().last().toDomain()
            emit(
                ResultResponse.OnSuccess(response)
            )
        }

    }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun getAllFavouriteCities(): Flow<ResultResponse<List<FavouriteCityEntity>>> =
        flow {
            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.favouriteCityDao().getAllFavouriteCities().map { obj ->
                FavouriteCityEntity(
                    obj.cityName, obj.cityNameAR, obj.latitude, obj.longitude
                )
            }
            emit(
                ResultResponse.OnSuccess(response)
            )
        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun addCityToFavourite(favouriteCity: FavouriteCityEntity): Flow<ResultResponse<Unit>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.favouriteCityDao().addCityToFavourite(
                FavouriteCity(
                    favouriteCity.cityName,
                    favouriteCity.cityNameAR,
                    favouriteCity.latitude,
                    favouriteCity.longitude
                )
            )
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun deleteCityFromFavourite(favouriteCity: FavouriteCityEntity): Flow<ResultResponse<Unit>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.favouriteCityDao().deleteCityFromFavourite(
                FavouriteCity(
                    favouriteCity.cityName,
                    favouriteCity.cityNameAR,
                    favouriteCity.latitude,
                    favouriteCity.longitude
                )
            )
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun insertOneCallModel(oneCallModel: OneCall): Flow<ResultResponse<Unit>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.oneCallDao().insertOneCallModel(oneCallModel.fromDomain())
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun deleteOneCallModel(oneCallModel: OneCall): Flow<ResultResponse<Unit>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.oneCallDao().deleteOneCallModel(oneCallModel.fromDomain())
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun getUserAlerts(): Flow<ResultResponse<List<UserAlertsEntity>>> = flow {
        emit(ResultResponse.OnLoading(true))
        val response =
            appDatabase.alertDao().getUserAlerts().map { userAlert -> userAlert.toDomain() }
        emit(
            ResultResponse.OnSuccess(response)
        )

    }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun insertUserAlerts(userAlerts: UserAlertsEntity): Flow<ResultResponse<Long>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.alertDao().insertUserAlerts(userAlerts.fromDomain())
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e -> emit(ResultResponse.OnError(e.message ?: Constants.unknownError)) }

    override suspend fun deleteUserAlerts(userAlerts: UserAlertsEntity): Flow<ResultResponse<Unit>> =
        flow {

            emit(ResultResponse.OnLoading(true))
            val response = appDatabase.alertDao().deleteUserAlerts(userAlerts.fromDomain())
            emit(
                ResultResponse.OnSuccess(response)
            )

        }.catch { e ->
            emit(ResultResponse.OnError(e.message ?: Constants.unknownError))
        }

    override suspend fun deleteUserAlertsById(id: Int): Unit =
        appDatabase.alertDao().deleteUserAlertsById(id)


    override suspend fun getUserAlertsById(id: Int): UserAlertsEntity =
        appDatabase.alertDao().getUserAlertsById(id).toDomain()

    override suspend fun getWeatherForWorker(
        latitude: Double,
        longitude: Double,
        measurementUnit: String,
        language: String,
        apiKey: String
    ): OneCall = apiService.getCurrentWeather(
        latitude, longitude, measurementUnit, language, apiKey
    ).toDomain()

}