package com.example.weather_forcast_app.di

import com.example.domain.repository.Repository
import com.example.domain.useCases.GetCurrentWeatherUseCase
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

}
