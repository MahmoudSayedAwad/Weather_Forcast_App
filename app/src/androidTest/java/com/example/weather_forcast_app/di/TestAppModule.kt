package com.example.weather_forcast_app.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Named("test_db")
    fun provideInMemoryDatabase(@ApplicationContext context:Context){
        Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).allowMainThreadQueries().build()
    }
}
