package com.example.weather_forcast_app.di

import android.content.Context
import com.example.data.utils.NetworkConnectivityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectionModule {
    @Singleton
    @Provides
    fun provideONetworkConnectivityChecker(@ApplicationContext context: Context): NetworkConnectivityChecker {
        return NetworkConnectivityChecker(context)
    }
}