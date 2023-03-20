package com.example.weather_forcast_app.di

import com.example.domain.repository.Repository
import com.example.domain.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetCurrentWeatherUseCase(
        repository: Repository,
    ): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(repository)
    }

    @Provides
    fun provideAddCityToFavourite(
        repository: Repository,
    ): AddCityToFavourite {
        return AddCityToFavourite(repository)
    }

    @Provides
    fun provideDeleteCityFromFavourite(
        repository: Repository,
    ): DeleteCityFromFavourite {
        return DeleteCityFromFavourite(repository)
    }

    @Provides
    fun provideDeleteOneCallModel(
        repository: Repository,
    ): DeleteOneCallModel {
        return DeleteOneCallModel(repository)
    }

    @Provides
    fun provideGetAllFavouriteCities(
        repository: Repository,
    ): GetAllFavouriteCities {
        return GetAllFavouriteCities(repository)
    }

    @Provides
    fun provideGetUserAlerts(
        repository: Repository,
    ): GetUserAlerts {
        return GetUserAlerts(repository)
    }

    @Provides
    fun provideInsertOneCallModel(
        repository: Repository,
    ): InsertOneCallModel {
        return InsertOneCallModel(repository)
    }

    @Provides
    fun provideInsertUserAlerts(
        repository: Repository,
    ): InsertUserAlerts {
        return InsertUserAlerts(repository)
    }

    @Provides
    fun provideDeleteUserAlerts(
        repository: Repository,
    ): DeleteUserAlerts {
        return DeleteUserAlerts(repository)
    }

    @Provides
    fun provideDeleteUserAlertsByID(
        repository: Repository,
    ): DeleteUserAlertsById {
        return DeleteUserAlertsById(repository)
    }

    @Provides
    fun provideGetUserAlertsByID(
        repository: Repository,
    ): GetUserAlertsById {
        return GetUserAlertsById(repository)
    }

    @Provides
    fun provideGetWeatherForWorkeer(
        repository: Repository,
    ): GetWeatherForWorkeer {
        return GetWeatherForWorkeer(repository)
    }

}
