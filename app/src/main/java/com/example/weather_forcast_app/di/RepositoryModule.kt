package com.example.weather_forcast_app.di

import com.example.data.api.ApiService
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        fun provideRepository(apiService: ApiService):Repository{
             return RepositoryImpl(apiService)
        }
    }
