package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.models.FavouriteCity
import com.example.data.models.OneCallResponse
import com.example.data.models.UserAlert

@Database(entities = [FavouriteCity::class, OneCallResponse::class, UserAlert::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteCityDao(): FavouriteCityDao
    abstract fun oneCallDao(): OneCallDao
    abstract fun alertDao(): AlertsDao
}