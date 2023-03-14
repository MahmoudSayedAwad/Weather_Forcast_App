package com.example.weather_forcast_app.di

import com.example.data.api.ApiService
import com.example.data.db.AppDatabase
import com.example.data.repository.RepositoryImpl
import com.example.data.utils.NetworkConnectivityChecker
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        fun provideRepository(apiService: ApiService,appDatabase: AppDatabase,networkConnectivityChecker: NetworkConnectivityChecker):Repository{
             return RepositoryImpl(apiService,appDatabase,networkConnectivityChecker)
        }
    }
