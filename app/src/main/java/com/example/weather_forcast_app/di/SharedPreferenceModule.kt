package com.example.weather_forcast_app.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weather_forcast_app.utils.Constants
import com.example.weather_forcast_app.utils.SharedPrefManger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SETTINGS_FILE, Context.MODE_PRIVATE)
    }
    @Provides
    @Singleton
    fun provideSharedPrefManger(sharedPreferences: SharedPreferences): SharedPrefManger{
        return SharedPrefManger(sharedPreferences)
    }
}

