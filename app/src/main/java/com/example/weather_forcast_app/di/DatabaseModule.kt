package com.example.weather_forcast_app.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context)= Room.databaseBuilder(context,AppDatabase::class.java,"weather_db").build()
    @Provides
    @Singleton
    fun provideFavouriteCityDao(db:AppDatabase)=db.favouriteCityDao()
    @Provides
    @Singleton
    fun provideAlertsDao(db:AppDatabase)=db.alertDao()
    @Provides
    @Singleton
    fun provideOneCallDao(db:AppDatabase)=db.oneCallDao()
}